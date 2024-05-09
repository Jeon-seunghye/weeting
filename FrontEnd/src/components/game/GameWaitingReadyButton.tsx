import { useState } from 'react';
import styles from '@/styles/game/GameWaitingReadyButton.module.css';
import { RoomInfo } from '@/types/game';
import { useNavigate } from 'react-router-dom';
import { userState } from '@/recoil/atom';
import { useRecoilValue, useSetRecoilState } from 'recoil';

const GameWaitingReadyButton = ({
  roomUsers,
  blink,
  onStartGame,
}: {
  roomUsers: RoomInfo['roomUsers'];
  blink?: boolean;
  onStartGame: () => void;
}) => {
  const [isReady, setIsReady] = useState(false);
  const navigate = useNavigate();
  const myId = useRecoilValue(userState);

  const ReadyHandler = () => {
    if (isFirstMember) {
      onStartGame();
    } else {
      setIsReady(!isReady);
    }
  };

  // 방장인 경우 반짝이도록 수정
  const isFirstMember = roomUsers[0];
  const buttonContent = isFirstMember ? '게임시작' : isReady ? '준비 취소' : '준비';

  let buttonStyle = `FontM32 ${styles.Btn} ${isFirstMember && blink ? styles.Blink : ''}`;

  if (isReady) {
    buttonStyle = `FontM32 ${buttonStyle} ${styles.Ready}`;
  }

  return (
    <>
      <button className={buttonStyle} onClick={ReadyHandler}>
        {buttonContent}
      </button>
    </>
  );
};

export default GameWaitingReadyButton;
