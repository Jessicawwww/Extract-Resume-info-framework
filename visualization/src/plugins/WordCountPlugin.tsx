import React from 'react';
import './WordCountPlugin.css';
import PersonalPagePlugin from '../framework/PersonalPagePlugin';
import Resume from '../framework/Resume';
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  Label
} from "recharts";


function WordCountPlugin(): PersonalPagePlugin {
    const name: string = "WordCount"

    return {
        getName(): string {
            return name;
        },

        getContent(resume: Resume): JSX.Element {
            const wordCountMap = new Map(Object.entries(resume.wordCount))
            
            const words = Array.from(wordCountMap.keys())
            const freq = Array.from(wordCountMap.values())

            let data: any[] = []

            for (var i = 0; i < words.length; i++) {
                data.push({ word: words[i], count: freq[i] })
            }
            data.sort((d1, d2)=> { return d2.count - d1.count});
            data = data.slice(0, 30);
            data.sort((d1, d2)=>{ 
                if (d1.word < d2.word) {
                    return -1
                }
                return 1
            });

            return (
                <div className="WordCountPlugin">
                    <div className='word-count-title-container'>
                        <p className='word-count-title'>W O R D&nbsp;&nbsp;&nbsp;F R E Q U E N C Y&nbsp;&nbsp;&nbsp;I N&nbsp;&nbsp;&nbsp;M Y&nbsp;&nbsp;&nbsp;R E S U M E</p>
                    </div>
                    <BarChart
                        width={1000}
                        height={500}
                        data={data}
                        margin={{
                        top: 10,
                        right: 5,
                        left: 5,
                        bottom: 10
                    }}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="word">
                    </XAxis>
                    <YAxis />
                    <Tooltip />
                    <Legend />
                    <Bar dataKey="count" fill="#d5e1f2" />
                  </BarChart>
                </div>
            )
        }
    }

}

export default WordCountPlugin;