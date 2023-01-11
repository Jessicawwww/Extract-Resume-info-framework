import React from 'react';
import './ContactInfoPlugin.css';
import PersonalPagePlugin from '../framework/PersonalPagePlugin';
import Resume from '../framework/Resume';
import ReactWordcloud, { Word } from 'react-wordcloud';


import 'tippy.js/dist/tippy.css';
import 'tippy.js/animations/scale.css';
import { ObjectType } from 'typescript';

function WordCloudPlugin(): PersonalPagePlugin {
  const name:string = "WordCloud"
  return {
    getName(): string {
      return name;
    },

    getContent(resume: Resume): JSX.Element {
      const wordCountMap = new Map(Object.entries(resume.wordCount));
      const array = Array.from(wordCountMap.entries());
      const words = Array.from(wordCountMap.keys())
      const freq = Array.from(wordCountMap.values())

      let data: any[] = []
      for (var i = 0; i < words.length; i++) {
          data.push({ text: words[i], value: freq[i]})
      }

      data.sort((d1, d2)=> { return d2.count - d1.count});
      data = data.slice(0, 50);
      data.sort((d1, d2)=>{ 
        if (d1.word < d2.word) {
          return -1
        }
        return 1
      });
        const callbacks = {
          // getWordColor: word => word.value > 1 ? "blue" : "red",
          onWordClick: console.log,
          onWordMouseOver: console.log,
          getWordTooltip: word => `${word.text} (${word.value}) [${word.value > 50 ? "good" : "bad"}]`,
        }
        const options = {
          rotations: 2,
          rotationAngles: [-90, 0],
        };
        const size = [1000, 800];
      return (
        <div className = "WordCloudPlugin">
          <div className = 'word-cloud-title-container'>
            <p className = 'word-cloud-title'>W O R D&nbsp;&nbsp;&nbsp;C L O U D</p>
          </div>
          <ReactWordcloud 
          callbacks={callbacks}
          words={data} />
        </div>
      )
    }
  }
}

// function WordCloudPlugin() {
//   return <ReactWordcloud words={words} />
// }
// const WordCloudPlugin = () => {
//   return (
//     <div>
      
//     </div>
//   );
// };
export default WordCloudPlugin;