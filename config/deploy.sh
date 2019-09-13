IMAGE_NAME:=config
IMAGE_VERSION:=v1
docker build -t gcr.io/$(PROJECT_ID)/$(IMAGE_NAME):$(IMAGE_VERSION) .
docker push gcr.io/$(PROJECT_ID)/$(IMAGE_NAME):$(IMAGE_VERSION)
kubectl apply -f k8s.yml
kubectl patch deployment $(IMAGE_NAME) -p "{\"spec\":{\"template\":{\"metadata\":{\"labels\":{\"date\":\"`date +'%s'`\"}}}}}"