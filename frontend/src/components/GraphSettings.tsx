import { useState } from "react";

const DIFFICULTIES = ["Easy","Medium","Hard","Custom"];

export default function GraphSettings(){
    const [chosenDifficulty, setChosenDifficulty] = useState<string>("Easy");
    const changeHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
        // console.log(e.target.value);
        setChosenDifficulty(e.target.value);
    }

    const isNodeSelectionDisables = chosenDifficulty !== "Custom";

    return(
        <div className="shadow bg-neutral-100 m-4 rounded-lg">
          <div className="flex h-full items-center gap-4 p-4 justify-between">
            <h1 className="text-4xl">The Graph Game</h1>
                <div className="">
                    {DIFFICULTIES.map((diff, i) => 
                        <div className="block" key={i}>
                            <input onChange={changeHandler} value={diff} type="radio" name="diff" checked={chosenDifficulty === diff}/>
                            <label htmlFor={"diff"}>{diff}</label>
                        </div>
                    )}
                </div>
                <div>
                    <div className="block">
                        <label htmlFor="startNode">Start Node</label>
                        <input onChange={changeHandler} type="text" name="startNode" disabled={isNodeSelectionDisables}/>
                    </div>
                    <div className="justify-between gap-4">
                        <label htmlFor="endNode">End Node</label>
                        <input onChange={changeHandler} type="text" name="endNode" disabled={isNodeSelectionDisables}/>
                    </div>
                </div>
            <button className="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded shadow text-lg">
              Play
            </button>
            {/* </div> */}
          </div>
        </div>
    )
}