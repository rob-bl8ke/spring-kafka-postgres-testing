// package com.cyg.demo.repositories;

// import io.vertx.core.Future;
// import io.vertx.core.Promise;
// import io.vertx.core.Vertx;
// import io.vertx.core.json.JsonArray;
// import io.vertx.circuitbreaker.CircuitBreaker;
// import io.vertx.circuitbreaker.CircuitBreakerOptions;
// import io.vertx.ext.jdbc.JDBCClient;
// import io.vertx.ext.sql.SQLConnection;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Repository;

// import java.util.Set;
// import java.util.concurrent.ConcurrentHashMap;

// @Repository
// public class AuditRepositoryImpl implements AuditRepository {

//     private final JDBCClient jdbcClient;
//     private final Vertx vertx;
//     // Cache to store table names that have been created.
//     private final Set<String> createdTables = ConcurrentHashMap.newKeySet();

//     @Autowired
//     public AuditRepositoryImpl(JDBCClient jdbcClient, Vertx vertx) {
//         this.jdbcClient = jdbcClient;
//         this.vertx = vertx;
//     }

//     @Override
//     public Future<Void> insert(String tableName, AuditEvent auditEvent) {
//         // Validate table name to prevent SQL injection. Only allow alphanumerics and underscores.
//         if (!tableName.matches("[a-zA-Z0-9_]+")) {
//             return Future.failedFuture(new IllegalArgumentException("Invalid table name: " + tableName));
//         }
//         // Ensure table exists (with caching)
//         return ensureTableExists(tableName).compose(v -> {
//             // Create a new circuit breaker instance for this insert call.
//             CircuitBreakerOptions options = new CircuitBreakerOptions()
//                     .setMaxFailures(3)          // allow 3 failures before opening the circuit
//                     .setTimeout(5000)           // timeout for each operation (ms)
//                     .setResetTimeout(5000);     // time before attempting to re-close the circuit

//             CircuitBreaker breaker = CircuitBreaker.create("audit-cb", vertx, options);
//             // Start the insertion with exponential backoff (3 attempts: 100ms, 200ms, 400ms)
//             return executeWithRetry(breaker, tableName, auditEvent, 1, 100);
//         });
//     }

//     /**
//      * Ensures that the table exists. If not, creates it using Postgres' native "CREATE TABLE IF NOT EXISTS".
//      * Caches the table name once creation is attempted.
//      */
//     private Future<Void> ensureTableExists(String tableName) {
//         if (createdTables.contains(tableName)) {
//             return Future.succeededFuture();
//         }
//         String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
//                 "id SERIAL PRIMARY KEY, " +
//                 "type VARCHAR, " +
//                 "namespace VARCHAR, " +
//                 "name VARCHAR, " +
//                 "version VARCHAR, " +
//                 "event_id VARCHAR, " +
//                 "event_type VARCHAR, " +
//                 "correlation_id VARCHAR, " +
//                 "message_json TEXT, " +
//                 "insertion_time TIMESTAMP DEFAULT NOW()" +
//                 ")";
//         Promise<Void> promise = Promise.promise();
//         jdbcClient.getConnection(ar -> {
//             if (ar.succeeded()) {
//                 SQLConnection connection = ar.result();
//                 connection.execute(createTableSQL, res -> {
//                     connection.close();
//                     if (res.succeeded()) {
//                         createdTables.add(tableName);
//                         promise.complete();
//                     } else {
//                         promise.fail(res.cause());
//                     }
//                 });
//             } else {
//                 promise.fail(ar.cause());
//             }
//         });
//         return promise.future();
//     }

//     /**
//      * Executes the insert operation wrapped in a circuit breaker with exponential backoff.
//      *
//      * @param breaker   the circuit breaker instance
//      * @param tableName the table name
//      * @param auditEvent the audit event to insert
//      * @param attempt   current attempt number
//      * @param delay     delay in milliseconds before the next retry if needed
//      * @return a Future that completes when the insert succeeds or fails after retries.
//      */
//     private Future<Void> executeWithRetry(CircuitBreaker breaker, String tableName, AuditEvent auditEvent,
//                                             int attempt, long delay) {
//         return breaker.executeCommand(promise -> {
//             JsonArray params = new JsonArray()
//                     .add(auditEvent.getType())
//                     .add(auditEvent.getNamespace())
//                     .add(auditEvent.getName())
//                     .add(auditEvent.getVersion())
//                     .add(auditEvent.getEventId())
//                     .add(auditEvent.getEventType())
//                     .add(auditEvent.getCorrelationId())
//                     .add(auditEvent.getMessageJson());
//             String sql = "INSERT INTO " + tableName +
//                     " (type, namespace, name, version, event_id, event_type, correlation_id, message_json) " +
//                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//             jdbcClient.getConnection(ar -> {
//                 if (ar.succeeded()) {
//                     SQLConnection connection = ar.result();
//                     connection.updateWithParams(sql, params, res -> {
//                         connection.close();
//                         if (res.succeeded()) {
//                             promise.complete();
//                         } else {
//                             promise.fail(res.cause());
//                         }
//                     });
//                 } else {
//                     promise.fail(ar.cause());
//                 }
//             });
//         }).recover(throwable -> {
//             if (attempt < 3) {
//                 Promise<Void> retryPromise = Promise.promise();
//                 vertx.setTimer(delay, timerId -> {
//                     // Retry with exponential backoff (delay doubles on each attempt)
//                     executeWithRetry(breaker, tableName, auditEvent, attempt + 1, delay * 2)
//                         .onComplete(retryPromise);
//                 });
//                 return retryPromise.future();
//             } else {
//                 // Fallback: in future, send to DLQ. For now, log to System.out.
//                 System.out.println("Insertion failed after " + attempt + " attempts for AuditEvent: " +
//                         auditEvent.getEventId());
//                 return Future.failedFuture(new AuditEventInsertFailureException(
//                         "Failed to insert audit event after retries", throwable));
//             }
//         });
//     }
// }
