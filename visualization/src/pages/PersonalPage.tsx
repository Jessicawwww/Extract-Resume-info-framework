import './PersonalPage.css'
import React from 'react';
import { Link } from 'react-scroll';
import PersonalPageFramework from '../framework/PersonalPageFramework';
import PersonalPagePlugin from '../framework/PersonalPagePlugin'
import plugins from '../pluginloader'
import $ from 'jquery';
import Resume from '../framework/Resume';


interface Props {
    location: any
}

interface State {
    activePlugin: string | null
}

class PersonalPage extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            activePlugin: null
        }
    }

    componentDidMount(): void {
        $(window).scrollTop(0);
        this.setState({
            activePlugin: Object.keys(plugins)[0]
        });
        window.onscroll = this.handleScroll;
    }

    /**
     * Render navigation bar.
     * @returns navigation bar
     */
    renderNavBar =()=> {
        const navbar: JSX.Element[] = [];
        Object.keys(plugins).forEach(plugin => {
            navbar.push(
                <Link className="navbar-button" activeClass="active" smooth spy to={`${plugin}`} >
                    {plugin}
                </Link>
            )
        });
        return (
            <div className="navbar" id="navbar">
                {navbar}
            </div>
        )
    }

    setActivePlugin =(plugin: string)=> {
        this.setState({
            activePlugin: plugin
        });
    }

    isScrolledIntoView =(elem: string)=> {
        if ($(elem).length == 0) {
            console.log(111);
            return false;
        }
        var docViewTop = $(window).scrollTop();
        var docViewBottom = docViewTop + $(window).height();
    
        var elemTop = $(elem).offset().top;
        return (docViewBottom >= elemTop);
    }

    isScrolledOutOfView =(elem: string)=> {
        if ($(elem).length == 0) {
            return false;
        }
        var docViewTop = $(window).scrollTop();
        var docViewBottom = docViewTop + $(window).height();

        var elemTop = $(elem).offset().top;
        var elemBottom = elemTop + $(elem).height();
        return (docViewBottom > elemBottom + 200);
    }
    
    handleScroll=()=> {
        console.log(Object.keys(plugins))
        for (let i = 0; i < Object.keys(plugins).length; i++) {
            const pluginName: string = Object.keys(plugins)[i];
            const pluginId = `#${pluginName}`
            if (this.isScrolledIntoView(pluginId) && !this.isScrolledOutOfView(pluginId)) {
                this.setActivePlugin(pluginName);
                break;
            }
        }
    }

    /**
     * Render plugin sections.
     */
    renderPlugins =()=> {
        const pluginSections: JSX.Element[] = [];
        const resume: Resume = this.props.location.state.resume;
        Object.keys(plugins).forEach(pluginName => {
            pluginSections.push(
                <div id={`${pluginName}`} className="plugin-section">
                    <PersonalPageFramework resume={resume} plugin={plugins[pluginName]} active={this.state.activePlugin==pluginName}></PersonalPageFramework>
                </div>
            );
        });
        return pluginSections;
    }

    render(): React.ReactNode {
        return (
            <div className="PersonalPage">
                { this.renderNavBar() }
                <div id='plugins-container'>
                    { this.renderPlugins() }
                </div>
            </div>
        )
    }
}

export default PersonalPage;