package onextent.azure.keyvault.config

import java.io.IOException

import com.microsoft.azure.credentials.ApplicationTokenCredentials
import com.microsoft.azure.management.Azure
import com.microsoft.azure.management.keyvault.Vault
import com.microsoft.azure.{AzureEnvironment, CloudException}
import com.microsoft.rest.LogLevel
import com.typesafe.scalalogging.LazyLogging

object SecretValue extends LazyLogging {

  @throws[CloudException]
  @throws[IOException]
  private def authenticateToAzure(): Azure = {
    val credentials = new ApplicationTokenCredentials(
      System.getenv("AZURE_CLIENT_ID"),
      System.getenv("AZURE_TENANT_ID"),
      System.getenv("AZURE_CLIENT_SECRET"),
      AzureEnvironment.AZURE)
    logger.info(s"accessing Azure with client ID ${credentials.clientId}")
    Azure.configure
      .withLogLevel(LogLevel.BASIC)
      .authenticate(credentials)
      .withDefaultSubscription
  }

  private val vault: Vault =
    authenticateToAzure().vaults().getById(System.getenv("KEYVAULT_ID"))

  @throws[NullPointerException]
  def apply(secretName: String): String = {

    Option(vault.client().getSecret(vault.vaultUri(), secretName)) match {
      case Some(secretBundle) =>
        val secret = secretBundle.value()
        logger.debug(s"found secret for key '$secretName'")
        secret
      case _ =>
        logger.error(s"did not find $secretName")
        throw new NullPointerException(s"no secret for key '$secretName'")
    }

  }

}
