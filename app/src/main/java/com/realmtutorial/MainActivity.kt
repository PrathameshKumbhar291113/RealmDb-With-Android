package com.realmtutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.realmtutorial.models.Course
import com.realmtutorial.ui.theme.RealmTutorialTheme
import com.realmtutorial.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RealmTutorialTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val courses by viewModel.courses.collectAsState()

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        items(courses) { course ->
                            CourseItem(
                                course = course, modifier = Modifier
                                    .fillParentMaxWidth()
                                    .padding(16.dp)
                                    .clickable {
                                        viewModel.showCourseDetails(course)
                                    }
                            )
                        }

                    }

                    if (viewModel.courseDetails != null) {
                        Dialog(onDismissRequest = viewModel::hideCourseDetails) {
                            Column(
                                modifier = Modifier
                                    .widthIn(200.dp, 300.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(16.dp)
                            ) {
                                viewModel.courseDetails?.teacher?.address?.let { address ->
                                    Text(text = address.fullName)
                                    Text(text = address.street + " " + address.houseNumber)
                                    Text(text = address.zipCode.toString() + " " + address.city)
                                }
                                Button(
                                    onClick = viewModel::deleteCourse,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.errorContainer,
                                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                                    )
                                ) {
                                    Text(text = "Delete")
                                }
                            }
                        }
                    }
                }
            }

        }

    }
}

@Composable
fun CourseItem(
    course: Course,
    modifier: Modifier
) {
    Column(modifier = modifier.padding(horizontal = 8.dp, vertical = 10.dp)) {

        Text(
            text = "Course Name: ${course.name}",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            maxLines = 1,
        )
        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Teached By: ${course.teacher?.address?.fullName}",
            fontStyle = FontStyle.Italic,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Students Enrolled: ${course.enrolledStudents.joinToString { it.name }}",
            fontSize = 10.sp
        )
    }
}