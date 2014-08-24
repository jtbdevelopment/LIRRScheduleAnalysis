eventConfigureTomcat = { tomcat ->
    println "Configuring tomcat"
    tomcat.connector.setAttribute("compression", "on")
    tomcat.connector.port = serverPort
    tomcat.connector.setAttribute("compressableMimeType", "text/html,text/xml,text/plain,application/javascript")
}