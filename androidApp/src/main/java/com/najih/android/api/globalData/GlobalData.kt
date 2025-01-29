package com.najih.android.api.globalData


const val BASE_URL = "https://nserver.najih1.com/"
const val SIGNING_ENDPOINT = "users/login"
const val SIGNUP_ENDPOINT = "users/signup"
const val RECORDED_SUBJECTS_ENDPOINT="r_subjects"
const val STREAMS_SUBJECTS_ENDPOINT="t_subjects"
const val STREAMS_TEACHERS_ENDPOINT="apply_teacher/getTeachersForWeb/"
const val STREAMS_ENDPOINT="t_lessons/getLessonsForWeb/"
// EXAMS
const val EXAM_ENDPOINT = "exams"
const val SUBMIT_EXAM_ENDPOINT = "examResults"
const val USER_EXAMS_ENDPOINT = "examResults/getUserExams/byUserId"
// TEACHERS
const val GET_ACCEPTED_TEACHERS="apply_teacher?status=accepted"
// LATEST NEWS
const val GET_LATEST_NEWS="news"
// BUY LESSONS
const val SEND_PURCHASE_REQUEST_ENDPOINT="studentsLessons"
// UPLOAD FILE
const val UPLOAD_FILE_ENDPOINT="usersRouter/upload/all-files"
// RE FETCH USER INFO
const val RE_FETCH_USER_INFO="users/check/JWT"
// SIGN UP VERIFY EMAIL
const val VERIFY_EMAIL="users/verify-code"
const val RESEND_CODE="usersRouter/resendActivateEmail"
