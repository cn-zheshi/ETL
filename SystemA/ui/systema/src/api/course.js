import { get } from ".";

// 开放课程共享
export function openCourseShare(courseNo) {
  return get("selfCourseShare?courseNo=" + courseNo);
}

// 开放其它院系的课程共享
export function openOtherCourseShare(from, to, courseNo) {
  return get("otherCourseShare?from=" + from + "&to=" + to + "&courseNo=" + courseNo);
}

// 修改本院系课程分数
export function changeCourseScore(courseNo, studentNo, score) {
  return get("selfCourseScore?courseNo=" + courseNo + "&studentNo=" + studentNo + "&score=" + score);
}

// 修改其它院系课程分数
export function changeOtherCourseScore(from, to, courseNo, studentNo, score) {
  return get("otherCourseScore?from=" + from + "&to=" + to + "&courseNo=" + courseNo + "&studentNo=" + studentNo + "&score=" + score);
}