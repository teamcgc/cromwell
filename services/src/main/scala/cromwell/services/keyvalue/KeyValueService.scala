package cromwell.services.keyvalue

import cromwell.core.WorkflowId
import cromwell.services.ServiceRegistryActor.ServiceRegistryMessage

object KeyValueService {
  trait KvMessage
  trait KvAction extends KvMessage with ServiceRegistryMessage {
    def serviceName = "KeyValue"
  }
  trait KvResponse extends KvMessage

  case class KvServiceJobKey(callFqn: String, index: Option[Int], attempt: Int)
  case class KvScopedKey(workflowId: WorkflowId, jobKey: KvServiceJobKey, key: String)
  case class KvPut(pair: KvPair) extends KvAction
  case class KvGet(key: KvScopedKey) extends KvAction

  case class KvPair(key: KvScopedKey, value: Option[String]) extends KvResponse
  case class KvFailure(action: KvAction, failure: Throwable) extends KvResponse
  case class KvKeyLookupFailed(action: KvGet) extends KvResponse
  case class KvPutSuccess(action: KvPut) extends KvResponse
}