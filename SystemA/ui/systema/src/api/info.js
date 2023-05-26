import { get } from ".";

export function getStudentInfo(username) {
  return get("info?username=" + username + "&role=stud");
}

export function getSelfCourseInfo() {
  return get("selfCourseInfo");
}

// 获取本院系已选课程
export function getSelectedCourseInfo(studentNo) {
  return get("selfCourseSelected?studentNo=" + studentNo);
}

// 获取其它院系的课程
export function getOtherCourseInfo(from, to, studentNo) {
  return get("otherCourseInfo?from=" + from + "&to=" + to + "&studentNo=" + studentNo);
}

// 获取跨院系的选课信息
export function getOtherSelectedCourseInfo(from, to, studentNo) {
  return get("otherCourseSelected?from=" + from + "&to=" + to + "&studentNo=" + studentNo);
}

// 获取其它院系的所有学生信息
export function getOtherAllStudentInfo(from, to) {
  return get("otherAllStudentInfo?from=" + from + "&to=" + to);
}

