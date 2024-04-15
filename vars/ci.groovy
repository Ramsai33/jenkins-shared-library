def call() {
    pipeline {
        agent {
            label 'workstation'
        }
        stages {
            stage('compile') {
                steps {
                    common.compile()
                }
            }
            stage('unit test') {
                steps {
                    echo 'unit test'
                }
            }
            stage('quality check') {
                steps {
                    echo 'quality check'
                }
            }
            stage('storing') {
                steps {
                    echo 'storing'
                }
            }
        }

    }

}