import React from "react";
import SkillTag from "./SkillTag.jsx";

const SkillList = ({skillIds, allSkills, onRemove}) => {
    const selectedSkills = allSkills?.filter(skill =>
        skillIds.includes(skill.id)
    ) ?? [];

    return (
        <ul className="flex flex-wrap gap-2 mt-2">
            {selectedSkills.map(skill => (
                <SkillTag
                    key={skill.id}
                    skill={skill}
                    onRemove={onRemove}
                />
            ))}
        </ul>
    );
};

export default SkillList;