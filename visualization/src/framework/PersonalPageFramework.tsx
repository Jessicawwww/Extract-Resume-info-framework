import './PersonalPageFramework.css';
import React from 'react';
import PersonalPagePlugin from './PersonalPagePlugin';
import Resume from './Resume';
import { Transition } from 'semantic-ui-react'



interface Props {
    plugin: PersonalPagePlugin,
    active: boolean,
    resume: Resume
}

interface State {
    resume: Resume
}

class PersonalPageFramework extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
                // fake one for testing
                resume: this.props.resume
            }
        }

    render(): React.ReactNode {
        return (
            <Transition animation='fade up' duration={1000} visible={this.props.active}>
            <div className="PersonalPageFramework">
                {this.props.plugin.getContent(this.state.resume)}
            </div>
            </Transition>
        )
    }
}

export default PersonalPageFramework;