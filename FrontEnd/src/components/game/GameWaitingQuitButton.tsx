import styles from '@/styles/game/GameWaitingReadyButton.module.css';
import { useNavigate } from 'react-router-dom';
import { RoomInfo } from '@/types/game';

const GameWaitingQuitButton = (roomid: { roomid: RoomInfo["roomid"] }) => {
  const navigate = useNavigate();
  const QuitHandler = () => {
    navigate('/login');
  };
  return (
    <>
      <button className={`FontM32 ${styles.Btn}`} onClick={QuitHandler}>
        Quit
      </button>
    </>
  );
};

export default GameWaitingQuitButton;