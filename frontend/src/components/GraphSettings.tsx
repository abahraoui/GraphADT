import { observer } from "mobx-react-lite";
import { useRef } from "react";
import { DIFFICULTIES } from "../helpers/constants";
import { useStores } from "../helpers/useStores";
import { LoadingSpinner } from "./LoadingSpinner";

export default observer(function GraphSettings() {
  const {
    chosenDifficulty,
    setProp,
    selectedInput,
    startNode,
    endNode,
    canStartGame,
    isCreatingGraph,
    isPlaying,
    startPlaying,
    stopPlaying,
    submitGuess,
    scores,
  } = useStores();

  const guessRef = useRef<HTMLInputElement>(null);

  const onAbort = () => {
    if (!confirm("Are you sure you want to abort the game?")) return;
    stopPlaying();
  };

  const guess = () => {
    const guess = guessRef.current?.value;
    if (!guess) {
      alert("Please enter a guess");
      return;
    }
    submitGuess(parseInt(guess));
  };

  const isNodeSelectionDisabled = chosenDifficulty !== "Custom";
  const displayScoreBoard = scores?.length > 0;

  return (
    <>
      <div className="m-4 rounded-lg bg-neutral-100 shadow">
        <div className="flex h-full items-center justify-between gap-4 p-4">
          <h1 className="text-4xl font-semibold uppercase">The Graph Game</h1>
          {!isPlaying ? (
            <>
              <div className="flex items-center  gap-4">
                {DIFFICULTIES.map((diff, i) => (
                  <div className="flex flex-row" key={i}>
                    <input
                      onChange={() => setProp("chosenDifficulty", diff)}
                      value={diff}
                      type="radio"
                      name="diff"
                      id={`diff-${i}`}
                      checked={chosenDifficulty === diff}
                      className="h-5 w-5 rounded-full border-2 border-solid border-neutral-300"
                    />
                    <label
                      htmlFor={`diff-${i}`}
                      className="ml-2 text-sm font-medium text-gray-900"
                    >
                      {diff}
                    </label>
                  </div>
                ))}
              </div>
              <div className="flex gap-4">
                <InputField
                  title="Start node"
                  id="startNodeInput"
                  onClick={() => setProp("selectedInput", "START")}
                  disabled={isNodeSelectionDisabled}
                  focused={selectedInput === "START"}
                  content={startNode || ""}
                />
                <InputField
                  title="End node"
                  id="endNodeInput"
                  onClick={() => setProp("selectedInput", "END")}
                  disabled={isNodeSelectionDisabled}
                  focused={selectedInput === "END"}
                  content={endNode || ""}
                />
              </div>
            </>
          ) : (
            <div className="flex gap-2">
              <span>Difficulty:</span>
              <span className="mr-2 font-bold">{chosenDifficulty}</span>
              <span>Start node:</span>
              <span className="mr-2 font-bold">{startNode}</span>
              <span>End node:</span>
              <span className="font-bold">{endNode}</span>
            </div>
          )}
          <div className="flex items-center gap-2">
            {isPlaying && (
              <>
                <span>Guess:</span>
                <input
                  type="number"
                  ref={guessRef}
                  className="flex h-10 w-14 items-center justify-center rounded
                  border bg-gray-50
                  pl-2
                  transition-all aria-disabled:text-gray-400 aria-pressed:ring"
                  onKeyDown={(e) => {
                    if (e.key === "Enter") guess();
                  }}
                />
                <button
                  className="rounded bg-green-500 py-2 px-4 text-lg font-bold text-white shadow transition-all
          hover:bg-green-700 disabled:cursor-not-allowed disabled:opacity-50"
                  onClick={guess}
                  disabled={!canStartGame}
                >
                  Submit
                </button>
              </>
            )}
            {isCreatingGraph ? (
              <LoadingSpinner />
            ) : (
              !isPlaying && (
                <button
                  className="rounded bg-green-500 py-2 px-4 text-lg font-bold text-white shadow transition-all
          hover:bg-green-700 disabled:cursor-not-allowed disabled:opacity-50"
                  onClick={startPlaying}
                  disabled={!canStartGame}
                >
                  Play
                </button>
              )
            )}

            {isPlaying && (
              <button
                className="rounded bg-red-500 py-2 px-4 text-lg font-bold text-white shadow transition-all
          hover:bg-red-700 disabled:cursor-not-allowed disabled:opacity-50"
                onClick={onAbort}
              >
                Abort
              </button>
            )}
          </div>
        </div>
      </div>
      {displayScoreBoard && (
        <div className="absolute top-24 z-10 m-4 rounded-lg bg-neutral-100 p-4 shadow">
          <h2 className="mb-4 block text-2xl font-semibold uppercase">
            Scoreboard
          </h2>
          <ol className="flex flex-col gap-2">
            {scores?.map((score, i) => (
              <li key={i} className="flex flex-row gap-3">
                <span>{i + 1}.</span>
                <span>
                  {score.difficulty} from {score.startNode} to {score.endNode}
                </span>
                <span className="font-bold">{score.score}</span>
              </li>
            ))}
          </ol>
        </div>
      )}
    </>
  );
});

interface IInputProps {
  title: string;
  id: string;
  onClick: () => void;
  disabled: boolean;
  content: string;
  focused: boolean;
}

function InputField(props: IInputProps) {
  return (
    <div className="flex items-center gap-2">
      <label
        htmlFor={props.id}
        className="aria-disabled:text-gray-400"
        aria-disabled={props.disabled}
      >
        {props.title}
      </label>
      <div
        className="flex h-10 w-10 items-center justify-center
            rounded border
            bg-gray-50
            transition-all aria-disabled:text-gray-400 aria-pressed:ring"
        aria-disabled={props.disabled}
        aria-pressed={!props.disabled && props.focused}
        onClick={props.onClick}
      >
        {props.content}
      </div>
    </div>
  );
}
