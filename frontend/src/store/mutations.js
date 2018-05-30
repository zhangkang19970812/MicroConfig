// 使用常量替代 mutation 事件类型
// 把这些常量放在单独的文件中可以让你的代码合作者对整个 app 包含的 mutation 一目了然

// login
export const LOGIN = 'LOGIN'
export const LOGOUT = 'LOGOUT'

// steps
export const STEPS_INCRE = 'STEPS_INCRE'
export const STEPS_DECRE = 'STEPS_DECRE'
export const STEPS_SET = 'STEPS_SET'

// services
export const SERVICE_ADD = 'SERVICE_ADD'
export const SERVICE_DELETE = 'SERVICE_DELETE'

// zuul
export const RIBBON_ADD = 'RIBBON_ADD'
export const RIBBON_DELETE = 'RIBBON_DELETE'

// compose
export const COMPOSE_SERVICE_ADD = 'COMPOSE_SERVICE_ADD'
export const COMPOSE_SERVICE_DELETE = 'COMPOSE_SERVICE_DELETE'
