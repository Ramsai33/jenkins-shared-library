def call() {
    node('workstation') {
        if(!env.SONAR_EXTRA_OPTS) {
            env.SONAR_EXTRA_OPTS = " "
        }
        if(!env.TAG_NAME) {
            env.PUSH_CODE = "false"
        }
        else {
            env.PUSH_CODE = "true"
        }


        stage('Checkout') {
            cleanWs()
            git branch: 'main', url: "https://github.com/Ramsai33/cart.git"
            sh 'env'
        }

        stage('compile') {
            common.compile()
        }

        stage('unit test') {
            echo 'unit test'
        }

        stage('quality check') {
            SONAR_USER = '$(aws ssm get-parameters --region us-east-1 --names sonarqube.user  --with-decryption --query Parameters[0].Value | sed \'s/"//g\')'
            SONAR_PASS = sh(script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.pass  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
            wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${SONAR_PASS}", var: 'SECRET']]]) {
                //sh "sonar-scanner -Dsonar.host.url=http://172.31.18.167:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=${component} -Dsonar.qualitygate.wait=true ${SONAR_EXTRA_OPTS}"

            }
        }
        if(env.PUSH_CODE == "true") {
            stage('Package placing centralise') {
                echo 'Package placing centralise'
            }

        }


    }
}

