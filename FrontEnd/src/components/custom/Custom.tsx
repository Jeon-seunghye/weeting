import HomeFrame from '@/components/home/HomeFrame';
import styles from '@/styles/custom/Custom.module.css';
import Avatar from '@/components/avatar/Avatar';
import HomeButton from '@/components/home/HomeButton';
const Home = () => {
  return (
    <>
      <div className={styles.ButtonContainer}>
        <HomeButton {...{ message: '홈', direction: 'right', location: 'home' }} />
      </div>
      <div className={styles.AvatarContainer}>
        <Avatar {...{ move: true, size: 400, isNest: true }} />
      </div>
      <div className={styles.FrameContainer}>
        <HomeFrame />
      </div>
    </>
  );
};

export default Home;
