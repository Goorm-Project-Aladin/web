pipeline {
  agent any
  stages {
    stage('Git scm update') {
      steps {
        git url: 'https://github.com/Goorm-Project-Aladin/web.git', branch: 'main'
      }
    }
    stage('Docker build and push') {
      steps {
        sh '''
        aws ecr get-login-password --region ap-northeast-2 | docker login --username AWS --password-stdin 535954374321.dkr.ecr.ap-northeast-2.amazonaws.com
        docker build -t . aladin
        docker tag aladin:latest 535954374321.dkr.ecr.ap-northeast-2.amazonaws.com/aladin:latest
        docker push 535954374321.dkr.ecr.ap-northeast-2.amazonaws.com/aladin:latest
        '''
      }
    }
    stage('Clean up git file and docker image') {
      steps {
        sh '''
        rm -rf ./web
        docker rmi $(docker images -f "dangling=true" -q)
        '''
      }
    }
  }
}