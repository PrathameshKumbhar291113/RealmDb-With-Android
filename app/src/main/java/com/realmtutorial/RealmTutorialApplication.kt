package com.realmtutorial

import android.app.Application
import com.realmtutorial.models.Address
import com.realmtutorial.models.Course
import com.realmtutorial.models.Student
import com.realmtutorial.models.Teacher
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class RealmTutorialApplication: Application() {

    companion object{
        lateinit var realm: Realm
    }

    override fun onCreate() {
        super.onCreate()

        realm = Realm.open(
            configuration =  RealmConfiguration.create(
                schema = setOf(
                    Address::class,
                    Teacher::class,
                    Student::class,
                    Course::class
                )
            )
        )
    }

}