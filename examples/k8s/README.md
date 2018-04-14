```console
kubectl create secret generic azure-client-secret --from-literal AZURE_CLIENT_SECRET=SETME
```

```console
kubectl create configmap demo-vault-config --from-env-file=examples/k8s/test/demoConfigMap.yaml
```

```console
kubectl create -f examples/k8s/test/initDeployment.yaml
```

```console
kubectl create -f examples/k8s/test/sidecarDeployment.yaml
```

