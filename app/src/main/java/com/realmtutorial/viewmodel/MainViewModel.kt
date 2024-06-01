package com.realmtutorial.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.realmtutorial.RealmTutorialApplication
import com.realmtutorial.models.Address
import com.realmtutorial.models.Course
import com.realmtutorial.models.Student
import com.realmtutorial.models.Teacher
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val realm = RealmTutorialApplication.realm

    val courses = realm
        .query<Course>()
        .asFlow()
        .map { result ->
            result.list.toList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    init {
//        createSampleEntries()
    }

    private fun createSampleEntries() {
        viewModelScope.launch {
            realm.write {

                val address1 = Address().apply {
                    fullName = "Prathamesh Kumbhar"
                    street = "Juinagar"
                    houseNumber = 111
                    zipCode = 400705
                    city = "Navi Mumbai"
                }

                val address2 = Address().apply {
                    fullName = "New User Name 2"
                    street = "New Street 2"
                    houseNumber = 1142
                    zipCode = 400702
                    city = "Mumbai2"
                }

                val address3 = Address().apply {
                    fullName = "New User Name 3"
                    street = "New Street 3"
                    houseNumber = 1143
                    zipCode = 400707
                    city = "Mumbai3"
                }
                val address4 = Address().apply {
                    fullName = "New User Name 4"
                    street = "New Street 4"
                    houseNumber = 1144
                    zipCode = 400707
                    city = "Mumbai4"
                }

                val course1 = Course().apply {
                    name = "Kotlin Programming Course"
                }

                val course2 = Course().apply {
                    name = "Java Programming Course"
                }

                val course3 = Course().apply {
                    name = "Python Programming Course"
                }

                val course4 = Course().apply {
                    name = "Android Programming Course"
                }

                val course5 = Course().apply {
                    name = "IOS Programming Course"
                }

                val teacher1 = Teacher().apply {
                    address = address1
                    courses = realmListOf(course1, course2, course5)
                }

                val teacher2 = Teacher().apply {
                    address = address2
                    courses = realmListOf(course2, course5)
                }

                val teacher3 = Teacher().apply {
                    address = address3
                    courses = realmListOf(course1, course2, course3, course4)
                }

                val teacher4 = Teacher().apply {
                    address = address4
                    courses = realmListOf(course3)
                }

                course1.teacher = teacher1
                course2.teacher = teacher1
                course5.teacher = teacher1

                course2.teacher = teacher2
                course5.teacher = teacher2

                course1.teacher = teacher3
                course2.teacher = teacher3
                course3.teacher = teacher3
                course4.teacher = teacher3

                course3.teacher = teacher4


                address1.teacher = teacher1
                address2.teacher = teacher2
                address3.teacher = teacher3
                address4.teacher = teacher4

                val student1 = Student().apply {
                    name = "New Student 1"
                }

                val student2 = Student().apply {
                    name = "New Student 2"
                }

                course1.enrolledStudents.add(student1)
                course2.enrolledStudents.add(student2)
                course3.enrolledStudents.addAll(listOf(student1, student2))
                course4.enrolledStudents.add(student1)
                course5.enrolledStudents.add(student2)

                copyToRealm(teacher1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(teacher2, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(teacher3, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(teacher4, updatePolicy = UpdatePolicy.ALL)

                copyToRealm(course1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(course2, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(course3, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(course4, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(course5, updatePolicy = UpdatePolicy.ALL)

                copyToRealm(student1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(student2, updatePolicy = UpdatePolicy.ALL)

            }
        }
    }
}