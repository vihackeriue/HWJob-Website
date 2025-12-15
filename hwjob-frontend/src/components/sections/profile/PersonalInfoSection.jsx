import React from "react";
import SecondTitle from "../../ui/title/SecondTitle";
import {GENDER} from "../../../config/constants.jsx";
import useAuth from "../../../hooks/useAuth.jsx";
import {ROLES} from "../../../config/roles.jsx";
import {hasRole} from "../../../utils/permission.jsx";


const PersonalInfoSection = ({personalInfo}) => {

    const {auth} = useAuth();

    if (!personalInfo) return null;

    return (
        <div className="flex flex-col gap-3">

            <div className="bg-white dark:bg-stoneBrown-900/50 p-3 rounded-2xl">
                <SecondTitle>Thông tin cá nhân</SecondTitle>
                <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 mt-2">

                    <InformationField
                        label="Tên đầy đủ"
                        value={personalInfo.fullName}
                    />
                    <InformationField
                        label="Email"
                        value={personalInfo.email}
                    />
                    <InformationField
                        label="Số điện thoại"
                        value={personalInfo.phone}
                    />
                    <InformationField
                        label="Khu vực"
                        value={personalInfo.region?.name}
                    />

                    {/*CANDIDATE*/}

                    {hasRole(auth, ROLES.CANDIDATE) && (
                        <>
                            <InformationField
                                label="Ngày sinh"
                                value={personalInfo.dob}
                            />
                            <InformationField
                                label="Giới tính"
                                value={
                                    GENDER.find(g => g.code === personalInfo.gender)?.name
                                }
                            />
                            <InformationField
                                label="Trình độ"
                                value={personalInfo.education}
                            />
                            <InformationField
                                label="Mức lương mong muốn"
                                value={personalInfo.expectSalary}
                            />
                        </>
                    )}

                    {/*RECRUITER*/}
                    {hasRole(auth, ROLES.RECRUITER) && (
                        <>
                            <InformationField
                                label="Website"
                                value={personalInfo.website}
                            />
                        </>
                    )}


                </div>
            </div>

            <div className="bg-white dark:bg-stoneBrown-900/50 p-3 rounded-2xl">
                <SecondTitle>Tóm tắt</SecondTitle>
                {personalInfo.summary ? (
                    <div
                        className="prose max-w-none mt-2 p-3"
                        dangerouslySetInnerHTML={{
                            __html: personalInfo.summary,
                        }}
                    />
                ) : (
                    <span className="italic text-gray-400">Không có</span>
                )}
            </div>
            {hasRole(auth, ROLES.CANDIDATE) && (
                <>
                    <div className="bg-white dark:bg-stoneBrown-900/50 p-4 rounded-2xl">
                        <SecondTitle>Kỹ năng</SecondTitle>
                        {personalInfo.skills.length > 0 ? (
                            <SkillList skills={personalInfo.skills}/>
                        ) : (
                            <span className="italic text-gray-400">Không có</span>
                        )
                        }
                    </div>
                </>
            )
            }
        </div>
    );
};

const InformationField = ({label, value}) => {
    const isEmpty =
        value === null ||
        value === undefined ||
        value === "";

    return (
        <p className="bg-white dark:bg-stoneBrown-900 p-2 rounded-lg">
            <span className="font-bold">{label}: </span>
            {isEmpty ? (
                <span className="italic text-gray-400">Không có</span>
            ) : (
                value
            )}
        </p>
    );
};

const SkillList = ({skills}) => (
    <ul className="flex flex-wrap gap-2 mt-2">
        {skills.map((skill) => (
            <SkillTag key={skill.id} skill={skill}/>
        ))}
    </ul>
);

const SkillTag = ({skill}) => (
    <li
        className="
            inline-flex max-w-full
            items-center
            rounded-full
            border border-sky-100
            bg-sky-50
            dark:text-sky-300
            dark:border-sky-500/15
            dark:bg-sky-500/10
            px-3 py-1
            text-sm
            whitespace-normal
        "
    >
        {skill.name}
    </li>
);

export default PersonalInfoSection;