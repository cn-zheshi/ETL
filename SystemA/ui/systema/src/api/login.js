import { get } from ".";

export function studentLogin(username, password) {
  return get("login?username=" + username + "&password=" + password + "&role=stud");
}

// 管理员登录
export function adminLogin(username, password) {
  return get("login?username=" + username + "&password=" + password + "&role=admi");
}

