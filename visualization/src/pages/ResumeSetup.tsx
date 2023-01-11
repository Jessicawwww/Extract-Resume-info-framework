import './ResumeSetup.css'
import React, { ChangeEvent } from 'react';
import { Button, Select, Input, TextArea, Form, Transition, Message } from 'semantic-ui-react'
import $ from 'jquery';
import axios from 'axios';
import Resume from '../framework/Resume';
import plugins from '../pluginloader';

interface Props {
    history: any
}

interface State {
    dataPlugins: any
    dataPluginSelected: string | null,
    resume: any,
    photo: string,
    introduction: string,
    showStepTwo: boolean,
    parseSuccess: boolean
}

class ResumeSetup extends React.Component<Props, State> {
    constructor(props: Props) {
        super(props);
        this.state = {
            showStepTwo: false,
            dataPlugins: [
                { key: '', text: '', value: '' },
            ],
            dataPluginSelected: null,
            introduction: '',
            photo: '',
            resume: {},
            parseSuccess: false
        }
    }

    componentDidMount(): void {
        this.getDataPlugins();
    }

    /**
     * Get a list of data plugins option.
     */
    getDataPlugins =async()=> {
        const response = await axios.get('/getDataPlugins');
        const plugins: { key: string; text: string; value: string; }[] = [];
        response.data.plugins.forEach((plugin: string) => {
            plugins.push(
                { key: plugin, text: plugin, value: plugin }
            );
        });
        this.setState({
            dataPlugins: plugins,
            dataPluginSelected: plugins[0].key
        });
    }

    /**
     * Parse resume that user provides.
     */
    parseResume =async()=> {
        const response = await axios.get('/parseResume', {params:{
           resumePath: $('#resume-input').val(),
           pluginName: this.state.dataPluginSelected
        }});
        if (response.data.status == 1) {
            this.setState({
                resume: response.data.resume,
                showStepTwo: true,
                parseSuccess: true
            }, ()=> {
                $('#parse-success').css('display', 'block');
                $('#parse-success').css('visibility', 'visible');
                $('#parse-error').css('display', 'none');
                $('#parse-error').css('visibility', 'hidden');
            });
            $('html, body').animate({
                scrollTop: $("#step-two").offset().top
            }, 1000);
        } else {
            // Show error message
            $('#parse-error').text(response.data.message);
            this.setState({
                parseSuccess: false
            }, ()=>{
                $('#parse-error').css('display', 'block');
                $('#parse-error').css('visibility', 'visible');
                $('#parse-success').css('visibility', 'hidden');
                $('#parse-success').css('display', 'none');
            })
        }
    }

    /**
     * Check photo and introduction.
     * @returns true if they are valid
     */
    checkIntroAndPhoto =()=> {
        $('#intro-error').css('display', 'block');
        $('#intro-error').css('visibility', 'hidden');
        $('#photo-error').css('display', 'none');
        $('#photo-error').css('visibility', 'hidden');
        let flag: boolean = true;
        if (this.state.photo === null || this.state.photo === undefined || this.state.photo === '') {
            $('#intro-error').css('display', 'none');
            $('#photo-error').css('display', 'block');
            $('#photo-error').css('visibility', 'visible');
            flag = false;
        }
        const intro = $('#introduction-area').val();
        if (intro === '' || intro === undefined || intro === null) {
            $('#intro-error').css('display', 'block');
            $('#intro-error').css('visibility', 'visible');
            flag = false;
        }
        return flag;
    }

    /**
     * Get the resume data and redirect to personal page.
     */
    genPersonalPage =async()=> {
        if (this.checkIntroAndPhoto()) {
            this.state.resume.photo = this.state.photo;
            this.state.resume.introduction = $('#introduction-area').val();
            this.props.history.push('/personal_page', { resume: this.state.resume });
        }
    }

    /**
     * Trigger upload photo.
     */
    uploadPhoto =()=> {
        $('#photo-input').click();
    }

    /**
     * Preview the loaded photo.
     * @param event file change event
     */
    loadPhoto =(event: ChangeEvent<HTMLInputElement>)=> {
        const photoUrl: string = URL.createObjectURL(event.target.files![0]);
        $('#photo').attr('src', photoUrl);
        this.setState({
            photo: photoUrl
        })
    }

    /**
     * Set the data plugin.
     * @param event select change event
     */
    setDataPlugin =(event: React.SyntheticEvent<HTMLElement, Event>, data: any)=> {
        this.setState({
           dataPluginSelected: data.value
        })
    }

    render(): React.ReactNode {
        return (
            <div className="ResumeSetup">
                <div id='bg-top'>
                    <div id='title-container'>
                        <p className='title'>Build your personal website</p>
                        <p className='title'>from resume.</p>
                        <p className='sub-title'>Yes, it's that simple, in two easy steps.</p>
                    </div>
                    <img id='background-img' src='./background.png'/>
                    <div id='resume-input-container'>
                        <Input id='resume-input' placeholder='Your resume full path...  e.g. D:\resume.pdf' action>
                            <input />
                            <Select compact options={this.state.dataPlugins} placeholder='Format' onChange={this.setDataPlugin}/>
                            <Button id='parse-button' onClick={this.parseResume}>Next</Button>
                        </Input>
                    </div>
                    <Message negative id='parse-error'>
                        <p>&nbsp;</p>
                    </Message>
                    <Message positive id='parse-success'>
                        <p>Successfully parse your resume!</p>
                    </Message>
                </div>
                <div id='step-two'>
                    <Transition id='step-two-transition' animation='fade down' duration={1500} visible={this.state.showStepTwo}>
                        <div><div id='bg-bottom'>
                            <div className='photo-container'>
                                <img id='photo' onClick={this.uploadPhoto} src="./default_avatar.jpg" />
                                <input type='file' id='photo-input' onChange={this.loadPhoto}/>
                            </div>
                            <div className='introduction-container'>
                                <Form>
                                    <p className='title'>Tell us more about yourself</p>
                                    <TextArea id='introduction-area' placeholder='Your introduction...' style={{ minHeight: 200 }} />
                                </Form>
                                <Button id='generate-button' onClick={this.genPersonalPage}>Generate</Button>
                                <Message negative id='intro-error'>
                                    <p>Introduction could not be empty.</p>
                                </Message>
                                <Message negative id='photo-error'>
                                    <p>Please upload your photo!</p>
                                </Message>
                            </div>
                        </div></div>
                    </Transition>
                </div>
            </div>
        )
    }
}

export default ResumeSetup;