import { useState } from "react";

const DIFFICULTIES = ["Easy", "Medium", "Hard", "Custom"];

export default function GraphSettings(props: {
  startNode?: string;
  endNode?: string;
}) {
  const [chosenDifficulty, setChosenDifficulty] = useState<string>("Easy");
  const [selectedInput, setSelectedInput] = useState<"START" | "END" | null>(
    null
  );
  const changeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    setChosenDifficulty(e.target.value);
  };

  const isNodeSelectionDisables = chosenDifficulty !== "Custom";

  return (
    <div className="shadow bg-neutral-100 m-4 rounded-lg">
      <div className="flex h-full items-center gap-4 p-4 justify-between">
        <h1 className="text-4xl">The Graph Game</h1>
        <div className="flex items-center  gap-4">
          {DIFFICULTIES.map((diff, i) => (
            <div className="flex flex-row" key={i}>
              <input
                onChange={changeHandler}
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
            onClick={() => setSelectedInput("START")}
            disabled={isNodeSelectionDisables}
            focused={selectedInput === "START"}
            content={props.startNode || ""}
          />
          <InputField
            title="End node"
            id="endNodeInput"
            onClick={() => setSelectedInput("END")}
            disabled={isNodeSelectionDisables}
            focused={selectedInput === "END"}
            content={props.endNode || ""}
          />
        </div>
        <button className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded shadow text-lg">
          Play
        </button>
      </div>
    </div>
  );
}

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
    <div className="flex gap-2 items-center">
      <label
        htmlFor={props.id}
        className="aria-disabled:text-gray-400"
        aria-disabled={props.disabled}
      >
        {props.title}
      </label>
      <div
        className="w-10 h-10 bg-gray-50 rounded border
        aria-pressed:ring transition-all
        aria-disabled:text-gray-400
        flex items-center justify-center"
        aria-disabled={props.disabled}
        aria-pressed={!props.disabled && props.focused}
        onClick={props.onClick}
      >
        {props.content}
      </div>
    </div>
  );
}
