package cromwell.services.keyvalue.impl

import akka.actor.ActorSystem
import cromwell.core.ExecutionIndex._
import cromwell.core.WorkflowId
import cromwell.database.Database
import cromwell.services.keyvalue.KeyValueService.KvServiceJobKey
import cromwell.util.DatabaseUtil._

import scala.concurrent.{ExecutionContext, Future}

trait BackendKeyValueDatabaseAccess { this: Database =>

  def getBackendValueByKey(workflowId: WorkflowId, callFqn: String, callIndex: Option[Int], attempt: Int, key: String)
                           (implicit ec: ExecutionContext): Future[Option[String]] = {
    databaseInterface.queryBackendStoreValueByStoreKey(workflowId.toString, callFqn, callIndex.fromIndex, attempt, key)
  }

  def updateBackendKeyValuePair(workflowId: WorkflowId,
                                jobKey: KvServiceJobKey,
                                backendStoreKey: String,
                                backendStoreValue: String)(implicit ec: ExecutionContext, actorSystem: ActorSystem): Future[Unit] = {

    withRetry (() =>
      databaseInterface.addBackendStoreKeyValuePair(workflowId.toString, jobKey.callFqn, jobKey.index.fromIndex, jobKey.attempt, backendStoreKey, backendStoreValue)
    )
  }

}
