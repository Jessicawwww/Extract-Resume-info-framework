import React from 'react';
import './ContactInfoPlugin.css';
import PersonalPagePlugin from '../framework/PersonalPagePlugin';
import Resume from '../framework/Resume';
import Table from 'rc-table';


function ContactInfoPlugin(): PersonalPagePlugin {
    
      const name: string = "ContactInfo"

    return {
        getName(): string {
            return name;
        },

        getContent(resume: Resume): JSX.Element {
          const data = [
            {
              name: resume.lastName,
              email: resume.email,
              phone: resume.phoneNumber,
            },
          ];
    
          const columns = [
            {
              title: "Name",
              dataIndex: "name",
              key: "name",
              width: 200,
            },
            {
              title: "Email",
              dataIndex: "email",
              key: "email",
              width: 200,
            },
            {
              title: "Phone",
              dataIndex: "phone",
              key: "phone",
              width:200,
            },
          ];

            return (
                <div className="ContactInfoPlugin">
                    <div className='contact-info-title-container'>
                        <p className='contact-info-title'>C O N T A C T&nbsp;&nbsp;&nbsp;M E</p>
                    </div>
                    <Table columns={columns} data={data} tableLayout="auto"/>
                </div>
                
            )
        }
    }

}

export default ContactInfoPlugin;