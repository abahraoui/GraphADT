import { observer } from "mobx-react-lite";
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
  } = useStores();

  const onAbort = () => {
    confirm("Are you sure you want to abort the game?");
  };

  const isNodeSelectionDisabled = chosenDifficulty !== "Custom";

  return (
    <div className="m-4 rounded-lg bg-neutral-100 shadow">
      <div className="flex h-full items-center justify-between gap-4 p-4">
        <h1 className="text-4xl">The Graph Game</h1>
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
        <div className="flex items-center gap-2">
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
