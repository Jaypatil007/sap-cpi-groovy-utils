import com.sap.gateway.ip.core.customdev.util.Message
import java.util.HashMap
import com.sap.it.api.securestore.SecureStoreService
import com.sap.it.api.securestore.UserCredential
import com.sap.it.api.securestore.exception.SecureStoreException
import com.sap.it.api.ITApiFactory

// Debug Logger Utility
class DebugLogger {
    private static final String LOG_PROPERTY = "DebugLogContents"
    private static final String ATTACHMENT_NAME = "CredentialRetrievalLogs"
    
    static void init(Message message) {
        message.setProperty(LOG_PROPERTY, "")
    }
    
    static void add(Message message, String text) {
        def timestamp = new Date().format("yyyy-MM-dd HH:mm:ss.SSS")
        def currentLog = message.getProperty(LOG_PROPERTY) ?: ""
        message.setProperty(LOG_PROPERTY, currentLog + timestamp + " - " + text + "\n")
    }
    
    static void write(Message message) {
        try {
            def logContent = message.getProperty(LOG_PROPERTY)
            if (logContent) {
                def messageLog = messageLogFactory.getMessageLog(message)
                if (messageLog != null) {
                    messageLog.addAttachmentAsString(ATTACHMENT_NAME, logContent, "text/plain")
                }
            }
        } catch (Exception e) {
            // Silently fail - we don't want to disrupt main processing
        }
    }
}

def Message processData(Message message) {
    // Initialize debug logging
    DebugLogger.init(message)
    DebugLogger.add(message, "Starting credential retrieval process")
  
    try {
        // Read from properties to make it more dynamic
        def mapProperties = message.getProperties()
        DebugLogger.add(message, "Retrieved message properties: " + mapProperties.keySet())
        
        def credentialName = mapProperties.get("credential_name_property_key")
        DebugLogger.add(message, "Credential name from properties: " + credentialName)

        // Credential specific code
        SecureStoreService secureStoreService = ITApiFactory.getService(SecureStoreService.class, null)
        DebugLogger.add(message, "SecureStoreService initialized successfully")
        
        UserCredential userCredential = secureStoreService.getUserCredential(credentialName)
        DebugLogger.add(message, "UserCredential retrieved for: " + credentialName)

        def user = userCredential.getUsername().toString()
        def pass = userCredential.getPassword().toString()
        
        // Mask password in logs for security
        DebugLogger.add(message, "Username retrieved: " + user)
        DebugLogger.add(message, "Password retrieved: " + (pass ? "*** MASKED ***" : "NULL/EMPTY"))

        message.setProperty("user", user)
        message.setProperty("pass", pass)
        
        DebugLogger.add(message, "Credentials successfully set as message properties")
        DebugLogger.add(message, "Process completed successfully")
        
    } catch (SecureStoreException e) {
        DebugLogger.add(message, "SecureStoreException: " + e.getMessage())
        throw e
    } catch (Exception e) {
        DebugLogger.add(message, "Unexpected error: " + e.getMessage())
        throw e
    } finally {
        // Always write the debug log, even if an exception occurred
        DebugLogger.write(message)
    }
    
    return message
}
