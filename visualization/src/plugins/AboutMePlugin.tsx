import React from 'react';
import './AboutMePlugin.css';
import PersonalPagePlugin from '../framework/PersonalPagePlugin';
import Resume from '../framework/Resume';

function AboutMePlugin(): PersonalPagePlugin {
    const name: string = "AboutMe"

    return {
        getName(): string {
            return name;
        },

        getContent(resume: Resume): JSX.Element {
            return (
                <div className="AboutMePlugin">
                    <img id='about-me-photo' src={resume.photo} />
                    <div id='about-me-intro'>
                        <p className='about-me-title'>A B O U T &nbsp;&nbsp;M E</p>
                        <p id='about-me-greeting'>Hi, I am {resume.firstName} {resume.lastName}.</p>
                        {resume.introduction}
                    </div>
                </div>
            )
        }
    }

}

export default AboutMePlugin;