[![Build Status](https://travis-ci.org/navicore/KeyVaultConfig.svg?branch=master)](https://travis-ci.org/navicore/KeyVaultConfig)

Azure Key Vault Secrets Init Container
-----

An 'init container' to override [Lightbend Config](https://github.com/lightbend/config) settings with values stored as secrets in Azure KeyVault.

The `initContainer` will look up secrets and store them in a file available to the application running in the same pod.

Configure the vault auth with environment vars via a configmap as:

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

Configure the config file entries with `SECRETS_SPEC` where each pair is `lightbendConfigPath:secretName`:
```yaml
env:
- name: SECRETS_SPEC
  value: "main.path:MAIN_PATH,main.port:MAIN_PORT"
```

Configure the location of the file via:
```yaml
env:
- name: SECRETS_FILE_LOCATION
  value: "/opt/config/overrides.yaml"
```

Change your application running in the container to support the presence of an override file:

```scala
  val conf = {
    new java.io.File("/opt/config/overrides.yaml") match {
      case file: java.io.File if file.exists() =>
        val overrides: Config = ConfigFactory.parseFile(file)
        overrides.withFallback(ConfigFactory.load())
      case _ => ConfigFactory.load()
    }
  }
  // use 'conf' to look up values as normal
  // ...
  // ...
  // ...
```

See `examples/k8s` dir for a working usage example.

