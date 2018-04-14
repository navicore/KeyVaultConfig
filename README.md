[![Build Status](https://travis-ci.org/navicore/KeyVaultConfig.svg?branch=master)](https://travis-ci.org/navicore/KeyVaultConfig)

Azure Key Vault Secrets Init Container and Sidecar Container
-----

An 'init container' to override [Lightbend Config](https://github.com/lightbend/config) settings with values stored as secrets in Azure KeyVault.

The `initContainer` will look up secrets and store them in a file available to the application running in the same pod.

Can also be configured as a sidecar if you don't want to persist secrets.

Configure the vault auth with environment vars (but with k8s configmaps and secrets) as:

```yaml
env:
- name: AZURE_CLIENT_ID
  value: "SET ME"
- name: AZURE_CLIENT_SECRET
  value: "SET ME"
- name: AZURE_TENANT_ID
  value: "SET ME"
- name: KEYVAULT_ID
  value: "/subscriptions/SETME/resourceGroups/SETME/providers/Microsoft.KeyVault/vaults/SETME"
```

Configure the config override entries with `SECRETS_SPEC` where each pair is `lightbendConfigPath:secretName`:
```yaml
env:
- name: SECRETS_SPEC
  value: "akka.contrib.persistence.mongodb.mongo.mongouri:PROD_DB_MONGOURI"
```

Configure the location of the file via:
```yaml
env:
- name: SECRETS_FILE_LOCATION
  value: "/opt/config/overrides.yaml"
```

To enable sidecar a container http://localhost:8998 http server, configure via:
```yaml
env:
- name: IS_SIDECAR
  value: "true"
```
Using the sidecar, no secrets are persisted and only containers in the same pod can access the localhost port - no networking and thus no https. 

NOTE: The sidecar may initialize too slowly for your app and your app will be restarted by Kubernetes until it successfully connects to the sidecar.

### App Changes

Change your application running in the container to support the presence of an override file:

```scala
  val conf: Config = sys.env
    .get("CONFIG_OVERRIDES_URL")
    .fold(ConfigFactory.load)(u =>
      ConfigFactory.parseURL(new URL(u)).withFallback(ConfigFactory.load))
  //
  // use 'conf' to look up values as normal
  // ...
  // ...
  // ...
```

then in your kubernetes yaml tell your app where to find the overrides.

for initContainer:
```console
        env:
        - name: CONFIG_OVERRIDES_URL
          value: "file:///opt/config/overrides.yaml"
```

for sidecar container:
```console
        env:
        - name: CONFIG_OVERRIDES_URL
          value: "http://localhost:8998/config"
```

### Examples

See [examples/k8s](examples/k8s) dir for a working examples.

