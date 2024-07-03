# Cloud Native Final 프로젝트 시연 관련

## 클라우드 네이티브 아키텍처(IaaS)
![Untitled](https://github.com/GyeoungJun/travel/assets/110224872/fcf3977d-c0a8-4787-ad31-ab8cd67e6376)

## 클라우드 네이티브 모델링(Biz.)
![image](https://github.com/GyeoungJun/travel/assets/110224872/1979e641-54c6-45bd-98d1-d6300c48028b)
![image](https://github.com/GyeoungJun/travel/assets/110224872/b5329f7d-b776-4f2d-83e1-6275101a42b7)

## 클라우드 네이티브 개발 MSA(Dev.)

### 분산 트랜잭션 - Saga

### 보상처리 - Compensation

### 단일 진입점 - Gateway
- Gateway를 사용하여 마이크로 서비스들의 엔드포인트 단일화

- ateway 를 사용하여 마이크로 서비스들의 엔드포인트 단일화

```java
server:
  port: 8088

---

spring:
  profiles: default
  cloud:
    gateway:
#<<< API Gateway / Routes
      routes:
        - id: flight
          uri: http://localhost:8082
          predicates:
            - Path=/flights/**, 
        - id: flightreservation
          uri: http://localhost:8083
          predicates:
            - Path=/flightReservations/**, 
        - id: payment
          uri: http://localhost:8084
          predicates:
            - Path=/payments/**, 
        - id: lodging
          uri: http://localhost:8085
          predicates:
            - Path=/lodgings/**, 
        - id: lodgingreservation
          uri: http://localhost:8086
          predicates:
            - Path=/lodgingReservations/**, 
        - id: mypage
          uri: http://localhost:8087
          predicates:
            - Path=, 
        - id: frontend
          uri: http://localhost:8080
          predicates:
            - Path=/**
```

### 분산 데이터 프로텍션 - CQRS

- MyFlightInfo
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/8132fd37-5ad5-4550-a491-2e6ea8822ca2)
- MyLodgingInfo
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/32b93c5c-72f3-45a2-b6c8-68cd8029c93c)

## 클라우드 네이티브 운영

### 클라우트 배포 - Container 운영

- ECR 생성
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/a224e185-89da-4d75-b84d-8b65c92d9182)
- CodeBuild 생성
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/2c01c910-1627-428a-8236-1b22aefcc2c2)
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/c843eb9c-0aee-4bd2-be4e-608e2ba4a4a8)
- Buildspec.yml 실행
  - buildspec.yml 실행
    
    ```java
    version: 0.2
    
    env:
      variables:
        IMAGE_REPO_NAME: "user01-travel-flight"
    
    phases:
      install:
        runtime-versions:
          java: corretto11
          docker: 20
      pre_build:
        commands:
          - echo Logging in to Amazon ECR...
          - echo $IMAGE_REPO_NAME
          - echo $AWS_ACCOUNT_ID
          - echo $AWS_DEFAULT_REGION
          - echo $CODEBUILD_RESOLVED_SOURCE_VERSION
          - echo start command
          - $(aws ecr get-login --no-include-email --region $AWS_DEFAULT_REGION)
      build:
        commands:
          - echo Build started on `date`
          - echo Building the Docker image...
          - mvn package -B -Dmaven.test.skip=true
          - docker build -t $AWS_ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com/$IMAGE_REPO_NAME:$CODEBUILD_RESOLVED_SOURCE_VERSION  .
      post_build:
        commands:
          - echo Build completed on `date`
          - echo Pushing the Docker image...
          - docker push $AWS_ACCOUNT_ID.dkr.ecr.$AWS_DEFAULT_REGION.amazonaws.com/$IMAGE_REPO_NAME:$CODEBUILD_RESOLVED_SOURCE_VERSION
    
    cache:
      paths:
        - '/root/.m2/**/*'
    ```
- CodeBuild 실행 시 프로젝트 Build 이후 ECR Push
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/9f503fc8-505c-4637-9920-67feb99c53d7)
- ECR에 해당 프로젝트 이미지가 Push된 모습
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/17bbdfcd-0361-4614-8c0a-150671ad5a80)

### 컨테이너 자동확장 - HPA
- “cpu-percent=50 : Pod 들의 요청 대비 평균 CPU 사용율(YAML Spec.에서 요청량이 200 milli-cores일때, 모든 Pod의 평균 CPU 사용율이 100 milli-cores(50%)를 넘게되면 HPA 발생)”
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/85430677-b90a-4789-bca9-4790797d3b72)

### 컨테이너로부터 환경분리 - ConfigMap/Secret
- config-dev.yaml 생성
  ```java
  apiVersion: v1
  kind: ConfigMap
  metadata:
    name: config-dev
    namespace: default
  data:
    FLIGHT_NAME: kimgyeongjun
    FLIGHT_LOG_LEVEL: INFO
  ```
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/fff0f812-6fd3-4ced-8841-3659398e760e)

- configMap의 내용 출력
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/29965078-2735-468c-b73c-027ee2a24b48)

- 실제 프로젝트 내 ConfigMap 설정 값 적용
  - application.yml
    ![image](https://github.com/GyeoungJun/travel/assets/110224872/696b044d-ff01-49b7-87db-40768ce2c4b8)
  - Deployment.yaml
    ```java
    apiVersion: apps/v1
    kind: Deployment
    metadata:
      name: flight
      labels:
        app: flight
    spec:
      replicas: 1
      selector:
        matchLabels:
          app: flight
      template:
        metadata:
          labels:
            app: flight
        spec:
          containers:
            - name: flight
              image: "kimgyeongjun/flight:0702"
              ports:
                - containerPort: 8080
              env:
              - name: FLIGHT_NAME
                valueFrom:
                  configMapKeyRef:
                    name: config-dev
                    key: FLIGHT_NAME
              - name: FLIGHT_LOG_LEVEL
                valueFrom:
                  configMapKeyRef:
                    name: config-dev
                    key: FLIGHT_LOG_LEVEL
    ```

- 실제 배포 시 Pod 내 ConfigMap 설정 결과 확인(logging level : INFO)
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/903a339d-8628-4589-8b74-2f18910b3eb2)

### 클라우드스토리지 활용 -PVC

- EFS 파일 시스템 생성
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/6e2559cd-40b8-40e0-a4b3-5f2987a2e62f)

- Kubernetes 서비스 계정(SA)과 IAM 역할 연결
  - 환경 변수 설정
    ```java
    export AWS_ROOT_UID=879772956301
    export REGION=ap-northeast-2
    export CLUSTER_NAME=user01-eks
    export FILE_SYSTEM_ID=fs-01fe6e8bb4598a977
    export MY_IMAGE_REPO=602401143452.dkr.ecr.ap-northeast-2.amazonaws.com
    ```
  - Kubernetes에 SA 생성, IAM 정책과 연결
    ```java
    eksctl create iamserviceaccount \
    --override-existing-serviceaccounts \
    --region $REGION \
    --name efs-csi-controller-sa \
    --namespace kube-system \
    --cluster $CLUSTER_NAME \
    --attach-policy-arn arn:aws:iam::$AWS_ROOT_UID:policy/EFSCSIControllerIAMPolicy \
    --approve 
    ```
  - IMAGE_REPO 참조
    ![image](https://github.com/GyeoungJun/travel/assets/110224872/bbb12794-04bf-4e8d-a25f-a121c0ec185f)
  - Cluster에 EFS CSI 드라이버 설치
    ```java
    helm repo add aws-efs-csi-driver https://kubernetes-sigs.github.io/aws-efs-csi-driver
    helm repo update aws-efs-csi-driver
    helm upgrade --install aws-efs-csi-driver aws-efs-csi-driver/aws-efs-csi-driver \
      --namespace kube-system \
      --set image.repository=602401143452.dkr.ecr.ap-northeast-2.amazonaws.com/eks/aws-efs-csi-driver \
      --set controller.serviceAccount.create=false \
      --set controller.serviceAccount.name=efs-csi-controller-sa
    ```
  - EFS csi Driver로 StorageClass 생성
    ```java
    kubectl apply -f - <<EOF
    kind: StorageClass
    apiVersion: storage.k8s.io/v1
    metadata:
      name: efs-sc
    provisioner: efs.csi.aws.com
    parameters:
      provisioningMode: efs-ap
      fileSystemId: $FILE_SYSTEM_ID
      directoryPerms: "700"
    EOF
    ```
    ![image](https://github.com/GyeoungJun/travel/assets/110224872/32fa35f6-6d56-4852-b0fd-fee8a5f04b88)
  - PVC 생성
    - flight-pvc.yaml
      ```java
      kubectl apply -f - <<EOF
      apiVersion: v1
      kind: PersistentVolumeClaim
      metadata:
        name: flight-pvc
        labels:
          app: flight
      spec:
        accessModes:
        - ReadWriteMany
        resources:
          requests:
            storage: 500Mi
        storageClassName: efs-sc
      EOF
      ```
  - 생성된 PVC Bound 확인
    ![image](https://github.com/GyeoungJun/travel/assets/110224872/61da7478-b4e3-4023-b479-8fb4e0b27288)
  - Deployment에 해당 PVC 연결 후 배포
        - Deployment.yaml
      ```java
      apiVersion: apps/v1
      kind: Deployment
      metadata:
        name: flight
        labels:
          app: flight
      spec:
        replicas: 1
        selector:
          matchLabels:
            app: flight
        template:
          metadata:
            labels:
              app: flight
          spec:
            containers:
              - name: flight
                image: "kimgyeongjun/flight:0702"
                ports:
                  - containerPort: 8080
                resources:
                  requests:
                    cpu: "200m" 
                readinessProbe:
                  httpGet:
                    path: '/actuator/health'
                    port: 8080
                  initialDelaySeconds: 10
                  timeoutSeconds: 2
                  periodSeconds: 5
                  failureThreshold: 10
                livenessProbe:
                  httpGet:
                    path: '/actuator/health'
                    port: 8080
                  initialDelaySeconds: 120
                  timeoutSeconds: 2
                  periodSeconds: 5
                  failureThreshold: 5
                volumeMounts:
                - mountPath: "/mnt/data"
                  name: volume
            volumes:
            - name: volume
              persistentVolumeClaim:
                claimName: flight-pvc  
      ```
    - Pod에 접속하여 Mount 된 위치에 txt 파일 생성
      ![image](https://github.com/GyeoungJun/travel/assets/110224872/bb29f65d-6f24-4513-bc6a-eadf8ebfbcba)
    - Pod 재배포 이후 txt 파일 새로 생성 / 기존 생성된 txt 파일 확인
      ![image](https://github.com/GyeoungJun/travel/assets/110224872/985cd973-46aa-4e23-844d-170090ecc7bf)

### 셀프 힐링/무정지배포 - Liveness/Readiness Probe

- 셀프힐링(LivenessProbe)
  - deployment.yaml
    ```java
    apiVersion: apps/v1
      kind: Deployment
      metadata:
        name: flight
        labels:
          app: flight
      spec:
        replicas: 1
        selector:
          matchLabels:
            app: flight
        template:
          metadata:
            labels:
              app: flight
          spec:
            containers:
              - name: flight
                image: "kimgyeongjun/flight:0702"
                ports:
                  - containerPort: 8080
                resources:
                  requests:
                    cpu: "200m" 
                readinessProbe:
                  httpGet:
                    path: '/actuator/health'
                    port: 8080
                  initialDelaySeconds: 10
                  timeoutSeconds: 2
                  periodSeconds: 5
                  failureThreshold: 10
                livenessProbe:
                  httpGet:
                    path: '/actuator/health'
                    port: 8080
                  initialDelaySeconds: 120
                  timeoutSeconds: 2
                  periodSeconds: 5
                  failureThreshold: 5
                volumeMounts:
                - mountPath: "/mnt/data"
                  name: volume
            volumes:
            - name: volume
              persistentVolumeClaim:
                claimName: flight-pvc  
    ```
  - 로컬에서 프로젝트 RUN시 LivenessProbe 확인
    ![image](https://github.com/GyeoungJun/travel/assets/110224872/30e8f2db-384c-47f2-8dca-72c49a21c515)
  - Flight 서비스에 라우터 생성(LoadBalancer 타입으로 변경)
    ```java
    kubectl expose deploy flight --type=LoadBalancer --port=8080
    kubectl get svc
    ```
    ![image](https://github.com/GyeoungJun/travel/assets/110224872/9ac8cb9d-d0cd-41ba-be8e-6803aac2de15)
  - External-IP 호출하여 LivenessProbe 확인
    ```java
    http a5c7cd28e3198433c808a86ca0ea9e0c-942751862.ap-northeast-    2.elb.amazonaws.com:8080/actuator/health
    ```
    ![image](https://github.com/GyeoungJun/travel/assets/110224872/30a4ba60-9131-4256-a03d-e78d5a8138a1)

- 무정지배포(Readiness Probe)
  - deployment.yaml
    ```java
    apiVersion: apps/v1
      kind: Deployment
      metadata:
        name: flight
        labels:
          app: flight
      spec:
        replicas: 1
        selector:
          matchLabels:
            app: flight
        template:
          metadata:
            labels:
              app: flight
          spec:
            containers:
              - name: flight
                image: "kimgyeongjun/flight:0702"
                ports:
                  - containerPort: 8080
                resources:
                  requests:
                    cpu: "200m" 
                readinessProbe:
                  httpGet:
                    path: '/actuator/health'
                    port: 8080
                  initialDelaySeconds: 10
                  timeoutSeconds: 2
                  periodSeconds: 5
                  failureThreshold: 10
                livenessProbe:
                  httpGet:
                    path: '/actuator/health'
                    port: 8080
                  initialDelaySeconds: 120
                  timeoutSeconds: 2
                  periodSeconds: 5
                  failureThreshold: 5
                volumeMounts:
                - mountPath: "/mnt/data"
                  name: volume
            volumes:
            - name: volume
              persistentVolumeClaim:
                claimName: flight-pvc  
    ```
  - 부하테스트를 위한 Pod 설치
    ```java
    kubectl apply -f - <<EOF
    apiVersion: v1
    kind: Pod
    metadata:
      name: siege
    spec:
      containers:
      - name: siege
        image: apexacme/siege-nginx
    EOF
    ```
  - ReadinessProbe가 없는 상태에서의 배포 테스트
    ![image](https://github.com/GyeoungJun/travel/assets/110224872/4f41b764-939b-47a7-aa9f-efbd8f57f9f5)
  - ReadinessProbe를 설정 한 상태에서의 배포 테스트
    ![image](https://github.com/GyeoungJun/travel/assets/110224872/f4eb360c-8be5-4c6f-a35e-b61048511a3d)

### 서비스 메쉬 응용 - Mesh
- Istio Service Mesh를 클러스터에 설치
    
    ```java
    export ISTIO_VERSION=1.18.7
    curl -L https://istio.io/downloadIstio | ISTIO_VERSION=$ISTIO_VERSION TARGET_ARCH=x86_64 sh -
    ```
    
- Istio 패키지 폴더로 이동
    
    ```java
    cd istio-$ISTIO_VERSION
    ```
    
- istioctl 클라이언트를 PATH에 잡아준다.
    
    ```java
    export PATH=$PWD/bin:$PATH
    ```
    
- Istio 설치
    
    ```java
    istioctl install --set profile=demo --set hub=gcr.io/istio-release
    ```
    
- Istio add-on Dashboard 설치
    
    ```java
    mv samples/addons/loki.yaml samples/addons/loki.yaml.old
    curl -o samples/addons/loki.yaml https://raw.githubusercontent.com/msa-school/Lab-required-Materials/main/Ops/loki.yaml
    kubectl apply -f samples/addons
    ```
    
- 모니터링 툴 설정
    
    ```java
    kubectl patch svc kiali -n istio-system -p '{"spec": {"type": "LoadBalancer"}}'
    
    kubectl patch svc tracing -n istio-system -p '{"spec": {"type": "LoadBalancer"}}'
    ```
    
- 서비스 메시 및 추적 서비스 확인
  ```java
  kubectl get service -n istio-system
  ```

  - Kiali 접속 확인
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/6386e837-9d4a-433d-ab73-ac5133f1d8f2)

  - JAEGER UI 접속 확인
  ![image](https://github.com/GyeoungJun/travel/assets/110224872/a1b8a24d-33f9-471e-8bf1-a0e329f81b13)

### 통합 모니터링 - Loggregation / Monitoring

- Loggregation
  - oggregation
    - 네임스페이스 생성, elasticsearch 설치
    
    ```jsx
    kubectl create namespace logging
    helm install elastic elastic/elasticsearch -f es-value.yml -n logging
    ```
    
    - ID / PW 정리
    
    ```jsx
    id : elastic
    passwd : BZ0EbOrSkXuK29x7
    
    kubectl get secrets --namespace=logging elasticsearch-master-credentials -ojsonpath='{.data.password}' | base64 -d
    ```
    
    - Fluent Bit 설치
    
    ```jsx
    git clone https://github.com/acmexii/fluent-bit-kubernetes-logging.git
    cd fluent-bit-kubernetes-logging/
    ```
    
    - Fluent DaemonSet이 사용할 계정과 권한 설정
    
    ```jsx
    kubectl create -f fluent-bit-service-account.yaml -n logging
    kubectl create -f fluent-bit-role.yaml -n logging
    kubectl create -f fluent-bit-role-binding.yaml -n logging
    ```
    
    - Fluent  DaemonSet의 환경설정을 확인하고 데몬셋 차레로 설치
    
    ```jsx
    kubectl apply -f fluent-bit-configmap-modified.yaml -n logging
    kubectl apply -f fluent-bit-ds-modified.yaml -n logging
    ```
    
    - Kibana 설치
    
    ```jsx
    helm show values elastic/kibana > kibana-value.yml
    
    helm install kibana elastic/kibana -f kibana-value.yml -n logging
    ```
    
    - Elasticserach 동작 확인
      ![image](https://github.com/GyeoungJun/travel/assets/110224872/fe463bd9-6526-4bdf-bb05-bbcff2f71fa4)

    - Kibana External-IP 확인
      ```java
      kubectl get svc -n logging
      ```
      ![image](https://github.com/GyeoungJun/travel/assets/110224872/0b3d8b74-60ee-4da0-8211-0d8f3b7b59e2)

    - Kibana Web Admin 접속
      ![image](https://github.com/GyeoungJun/travel/assets/110224872/0dd773ba-054a-46d6-b1cb-6d31917f7765)
      ![image](https://github.com/GyeoungJun/travel/assets/110224872/b9fc4869-e0d9-4c6c-8e21-2a658a9364d6)
    - 특정 네임스페이스 에서 로그 확인(예시 네임스페이스 : logging)
      ![image](https://github.com/GyeoungJun/travel/assets/110224872/ca50d855-6a3d-4b4a-bbb5-93a0e123c7d5)



