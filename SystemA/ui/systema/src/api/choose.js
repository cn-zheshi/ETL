import { get } from ".";

export function chooseCourse(courseNo, studentNo) {
  return get("selfCourseChoose?courseNo=" + courseNo + "&studentNo=" + studentNo);
}

// 退选本院系课程
export function cancelCourse(courseNo, studentNo) {
  return get("selfCourseDrop?courseNo=" + courseNo + "&studentNo=" + studentNo);
}

// 选择跨院系课程
export function chooseOtherCourse(from, to, studentNo, courseNo) {
  return get("otherCourseChoose?from=" + from + "&to=" + to + "&studentNo=" + studentNo + "&courseNo=" + courseNo);
}

// 退选其它院系课程
export function cancelOtherCourse(from, to, studentNo, courseNo) {
  return get("otherCourseDrop?from=" + from + "&to=" + to + "&studentNo=" + studentNo + "&courseNo=" + courseNo);
}
