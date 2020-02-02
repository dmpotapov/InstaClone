package yetanotherdima.instaclone.models

abstract class ModelBase {
    abstract fun toMap() : MutableMap<String, Any>
}