import { request } from 'umi';

export interface StateType {
  status?: 'ok' | 'error';
  currentAuthority?: 'user' | 'guest' | 'admin';
}

export interface UserRegisterParams {
  mail: string;
  password: string;
  confirm: string;
}

export async function doRegister(params: UserRegisterParams) {
  let result = await request("/api/register",{
      method:"POST",
      data:params,
  })
  console.log(result);
  return result;
}
