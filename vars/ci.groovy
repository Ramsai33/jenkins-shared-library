def call() {
    pipeline {
        agent {
            label 'workstation'
        }
        stages {
            stage('Hello') {
                steps {
                    script {
                        sh 'echo hello'
                    }
                }
            }
        }

    }

}