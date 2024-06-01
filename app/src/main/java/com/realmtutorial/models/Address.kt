package com.realmtutorial.models

import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

//Teacher -> Address (1-to-1) Relation
//Teacher -> Course (1-to-many) Relation
//Students -> Course (many-to-many) Relation

class Address: EmbeddedRealmObject {
    var fullName: String = ""
    var street: String = ""
    var houseNumber: Int = 0
    var zipCode: Int = 0
    var city: String = ""
    var teacher: Teacher? = null
}