import React from 'react';
import './TimelinePlugin.css';
import PersonalPagePlugin from '../framework/PersonalPagePlugin';
import Resume from '../framework/Resume';
import { Timeline, Event } from "react-timeline-scribble";

function TimelinePlugin(): PersonalPagePlugin {
    const name: string = "Timeline"

    return {
        getName(): string {
            return name;
        },

        getContent(resume: Resume): JSX.Element {
            const experiences: JSX.Element[] = []
            resume.experiences.forEach((experience) => {
                const descriptions: JSX.Element[] = []
                experience.description.forEach(description => {
                    descriptions.push(
                        <p>{description}</p>
                    )
                });
                experiences.push(
                    <Event interval={`${experience.startTime} – ${experience.endTime}`} title={experience.title} subtitle={experience.position}>
                    { descriptions }
                    </Event>
                )
            })
            return (
                <div className="TimelinePlugin">
                    <Timeline>
                        { experiences }
                    </Timeline>
                </div>
            )
        }
    }

}

export default TimelinePlugin;