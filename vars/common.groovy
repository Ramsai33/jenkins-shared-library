def compile() {
    if (app_lang == "nodejs") {
        sh 'npm install'
    }
}

def artifactPush() {
    if (app_lang == "nodejs") {
        sh "zip -r cart-${TAG_NAME}.zip node_modules server.js"
    }

}