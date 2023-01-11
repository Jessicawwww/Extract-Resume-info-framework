
export default interface Resume {
    firstName: string | null
    lastName: string | null
    email: string | null
    address: string | null
    phoneNumber: string | null
    url: string | null
    skills: string[]
    experiences: Experience[]
    wordCount: any
    introduction: string | undefined
    photo: string | undefined
}

interface Experience {
    title: string | null
    position: string | null
    location: string | null
    startTime: string | null
    endTime: string | null
    description: string[]
}
