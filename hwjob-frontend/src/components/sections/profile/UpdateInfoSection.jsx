import {useEffect, useState} from "react";
import SecondTitle from "../../ui/title/SecondTitle";
import FormInput from "../../ui/form/FormInput";
import FormSelect from "../../ui/form/FormSelect";
import {CiEdit} from "react-icons/ci";
import {GENDER} from "../../../config/constants";
import {UpdatePasswordDialog} from "../../dialog/UpdatePasswordDialog.jsx";
import {useList} from "../../../hooks/useList.jsx";
import {getRegionsNotPagination} from "../../../services/regionService.jsx";
import {IoMdClose} from "react-icons/io";
import useAuth from "../../../hooks/useAuth.jsx";

import {getSkillsNotPagination} from "../../../services/skillService.jsx";
import RichTextEditor from "../../ui/RichTextEditor.jsx";
import {useUpdateInfo} from "../../../hooks/user/useUpdateInfo.jsx";
import {toast} from "react-toastify";
import {ROLES} from "../../../constants/roles.jsx";
import {hasRole} from "../../../utils/permission.jsx";
import SkillList from "../../ui/form/SkillList.jsx";

const UpdateInfoSection = ({personalInfo, onUpdated}) => {

    const [formData, setFormData] = useState({
        // COMMON
        fullName: "",
        email: "",
        phone: "",
        summary: "",
        regionId: null,

        // CANDIDATE
        dob: "",
        gender: "",
        education: "",
        expectSalary: "",
        skillIds: [],

        // RECRUITER
        website: ""
    });

    useEffect(() => {
        if (!personalInfo) return;

        setFormData({
            // COMMON
            fullName: personalInfo.fullName ?? "",
            email: personalInfo.email ?? "",
            phone: personalInfo.phone ?? "",
            summary: personalInfo.summary ?? "",
            regionId: personalInfo.region?.id ?? null,

            // CANDIDATE
            dob: personalInfo.dob ?? "",
            gender: personalInfo.gender ?? "",
            education: personalInfo.education ?? "",
            expectSalary: personalInfo.expectSalary ?? "",
            skillIds: personalInfo.skills?.map(s => s.id) ?? [],

            // RECRUITER
            website: personalInfo.website ?? "",
        });
    }, [personalInfo]);

    const regions = useList(getRegionsNotPagination);
    const skills = useList(getSkillsNotPagination);

    const {auth} = useAuth();

    const [openUpdatePasswordDialog, setOpenUpdatePasswordDialog] = useState(false);

    const handleChange = (e) => {
        if (!e?.target) return;
        const {name, value} = e.target;
        setFormData(prev => ({...prev, [name]: value}));
    };

    const handleRegionChange = (e) => {
        if (!e?.target?.value) return;

        setFormData(prev => ({
            ...prev,
            regionId: Number(e.target.value)
        }));
    };

    const handleSummaryChange = (html) => {
        setFormData(prev => ({...prev, summary: html,}));
    };

    const handleSkillsChange = (e) => {
        const skillId = Number(e?.target?.value ?? e);
        if (!skillId) return;

        setFormData(prev => {
            if (prev.skillIds.includes(skillId)) {
                return prev;
            }

            return {
                ...prev,
                skillIds: [...prev.skillIds, skillId]
            };
        });
    };

    const handleRemoveSkill = (skillId) => {
        setFormData(prev => ({
            ...prev,
            skillIds: prev.skillIds.filter(id => id !== skillId)
        }));
    };

    const {updateProfile, loading} = useUpdateInfo(formData, auth);

    const handleSubmit = async () => {
        const success = await updateProfile();

        if (success) {
            onUpdated?.();
            toast.success("Cập nhật thông tin thành công!");
        } else {
            toast.success("Cập nhật thất bại!");
        }
    };

    if (!formData) return <div>Đang tải...</div>;

    return (
        <div className="flex flex-col gap-3">
            <div className="bg-white dark:bg-stoneBrown-900/50 p-3 rounded-2xl">
                <SecondTitle>Thông tin cá nhân</SecondTitle>
                <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 mt-2">

                    <FormInput
                        label="Tên đầy đủ"
                        name="fullName"
                        value={formData.fullName}
                        onChange={handleChange}
                        required
                    />

                    <FormInput
                        label="Email"
                        name="email"
                        type="email"
                        value={formData.email}
                        onChange={handleChange}
                        required
                    />

                    <FormInput
                        label="Số điện thoại"
                        name="phone"
                        value={formData.phone}
                        onChange={handleChange}
                        required
                    />

                    <FormSelect
                        label="Khu vực"
                        name="regionId"
                        selected={regions.data.find(r => r.id === formData.regionId)}
                        options={regions.data}
                        placeholder="Chọn khu vực"
                        onChange={handleRegionChange}
                    />
                    {hasRole(auth, ROLES.CANDIDATE) && (
                        <>
                            <FormSelect
                                label="Giới tính"
                                name="gender"
                                selected={GENDER.find(g => g.code === formData.gender)}
                                onChange={handleChange}
                                options={GENDER}
                                placeholder="Chọn giới tính"
                            />


                            <FormInput
                                label="Ngày sinh"
                                name="dob"
                                type="date"
                                value={formData.dob ?? ""}
                                onChange={handleChange}
                            />


                            <FormInput
                                label="Mức lương mong đợi (VNĐ)"
                                name="expectSalary"
                                type="number"
                                value={formData.expectSalary ?? ""}
                                onChange={handleChange}
                            />


                            <FormInput
                                label="Trình độ"
                                name="education"
                                value={formData.education ?? ""}
                                onChange={handleChange}
                            />

                        </>
                    )}

                    {hasRole(auth, ROLES.RECRUITER) && (
                        <>
                            <FormInput
                                label="Website"
                                name="website"
                                value={formData.website ?? ""}
                                onChange={handleChange}
                            />
                        </>
                    )}
                </div>

                {hasRole(auth, ROLES.CANDIDATE) && (
                    <div className="mt-3">
                        <SecondTitle></SecondTitle>
                        <span>Kỹ năng</span>
                        {formData.skillIds.length > 0 && (
                            <SkillList
                                skillIds={formData.skillIds}
                                allSkills={skills.data}
                                onRemove={handleRemoveSkill}
                            />
                        )}
                        <div className="mt-3">
                            <FormSelect
                                label="Thêm kỹ năng"
                                selected={null}
                                options={skills.data?.filter(
                                    skill => !formData.skillIds.includes(skill.id)
                                )}
                                placeholder="Chọn kỹ năng để thêm"
                                onChange={handleSkillsChange}
                            />
                        </div>
                    </div>
                )}
                <div className="mt-3">
                    <RichTextEditor
                        label="Tóm tắt"
                        value={formData.summary ?? ""}
                        onChange={handleSummaryChange}
                        placeholder="Giới thiệu..."
                    />
                </div>

                <div className="flex justify-end mt-4">
                    <button
                        className="flex gap-1 items-center bg-brightOrange hover:bg-brightOrange/90 px-4 py-2 rounded-lg text-white disabled:opacity-50 disabled:cursor-not-allowed transition-all"
                        onClick={handleSubmit}
                        disabled={loading}
                    >
                        <CiEdit size={20}/>
                        <span>Cập nhật</span>
                    </button>
                </div>
            </div>

            <div className="bg-white dark:bg-stoneBrown-900/50 p-3 rounded-2xl">
                <SecondTitle>Bảo mật</SecondTitle>
                <button
                    className="flex gap-1 items-center bg-brightOrange hover:bg-brightOrange/90 px-4 py-2 rounded-lg text-white my-3 transition-all"
                    onClick={() => setOpenUpdatePasswordDialog(true)}
                >
                    <CiEdit size={20}/>
                    Đổi mật khẩu
                </button>
                <UpdatePasswordDialog
                    open={openUpdatePasswordDialog}
                    onClose={() => setOpenUpdatePasswordDialog(false)}
                />
            </div>
        </div>
    );
};

export default UpdateInfoSection;
