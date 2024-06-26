import styles from '@/styles/main/MainLoginForm.module.css';
import { MainSignupFormPasswordProps } from '@/types/user';

const MainSignupFormPw = ({ password, onPasswordHandler }: MainSignupFormPasswordProps) => {
  return (
    <>
      <div className={styles.Mgb2}>
        <label className={`${styles.Label} FontM20Bold`} htmlFor="password">
          pw
        </label>
        <input
          className={`${styles.InputBox} FontM20`}
          id="password"
          type="password"
          placeholder="비밀번호를 입력하세요"
          value={password}
          onChange={onPasswordHandler}
          autoComplete='off'
        />
      </div>
    </>
  );
};

export default MainSignupFormPw;
