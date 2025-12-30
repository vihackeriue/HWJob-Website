import {IoMdClose} from "react-icons/io";
import React from "react";

const SkillTag = ({skill, onRemove}) => (
    <li
        className="
            inline-flex max-w-full
            items-center gap-1
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
        <span>{skill.name}</span>
        <button
            type="button"
            onClick={() => onRemove(skill.id)}
            className="
                hover:bg-sky-200
                dark:hover:bg-sky-500/20
                rounded-full
                p-0.5
                transition-colors
            "
            title="Xóa kỹ năng"
        >
            <IoMdClose size={16}/>
        </button>
    </li>
);

export default SkillTag