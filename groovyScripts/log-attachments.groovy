/*
 * SAP CPI Groovy Utility: Configurable Message Attachment Logger
 * 
 * Usage:
 * 1. Add this script to your integration flow
 * 2. In SAP CPI groovy script palette, set "Script Function" to the wrapper function name you want to call (e.g., log01)
 * 3. Set message property 'Logconfig' to 'true' (case-insensitive) to enable logging
 * 4. To add new attachment points:
 *    - Create new wrapper function (e.g., log04)
 *    - Maintain numbering convention in function name and ID parameter
 *    - Customize attachment name as needed
 */
import com.sap.gateway.ip.core.customdev.util.Message
import java.util.HashMap

// Wrapper functions with incremental numbering
def Message log01(Message message) {return processData("01 ", "Payload", message)}
def Message log02(Message message) {return processData("02 ", "Payload", message)}
def Message log03(Message message) {return processData("03 ", "Payload", message)}

// Core processing function with configurable logging
def Message processData(String id, String name, Message message) {
    // Check logging configuration from message properties
    def config = message.getProperties().get("Logconfig")
    def shouldLog = config?.toString()?.equalsIgnoreCase("true")
    
    // Only log if configuration is set to true
    if (shouldLog) {
        def body = message.getBody(java.lang.String) as String
        def messageLog = messageLogFactory.getMessageLog(message)
        if (messageLog != null) {
            messageLog.addAttachmentAsString(id + name, body, "text/plain")
        }
    }
    return message
}