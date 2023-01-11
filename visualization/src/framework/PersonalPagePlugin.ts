import Resume from "./Resume"

export default interface PersonalPagePlugin {
    // Get the name of the plugin, which will be displayed on the navigation bar.
    getName(): string
    // Get the content to be rendered on the page.
    getContent(resume: Resume): JSX.Element
}