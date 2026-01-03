import React, {useEffect, useState} from "react";
import {useList} from "../../../hooks/useList.jsx";
import {getRegionsNotPagination} from "../../../services/regionService.jsx";
import {getSkillsNotPagination} from "../../../services/skillService.jsx";
import {getJobTypesNotPagination} from "../../../services/jobTypeService.jsx";
import {getIndustriesNotPagination} from "../../../services/industryService.jsx";
import {getLevelsNotPagination} from "../../../services/levelService.jsx";
import {updateJobPost} from "../../../services/jobPostService.jsx";
import PrimaryTitle from "../../ui/title/PrimaryTitle.jsx";
import SecondTitle from "../../ui/title/SecondTitle.jsx";
import FormInput from "../../ui/form/FormInput.jsx";
import RichTextEditor from "../../ui/RichTextEditor.jsx";
import FormSelect from "../../ui/form/FormSelect.jsx";
import {SALARY_TYPE, STATUS_JOB_POST} from "../../../config/constants.jsx";
import SkillList from "../../ui/form/SkillList.jsx";
import PrimaryButton from "../../ui/button/PrimaryButton.jsx";
import {Dialog, DialogPanel, DialogTitle} from "@headlessui/react";
import {toast} from "react-toastify";

const UpdateJobPostDialog = ({
                                 open,
                                 onClose,
                                 onUpdate,
                                 jobPost
                             }) => {

    const [formJobPost, setFormJobPost] = useState(
        {
            title: "",
            description: "",
            quantity: 0,
            salary: 0,
            salaryType: null,
            status: "PUBLIC",
            endedTime: "",
            industryId: null,
            levelId: null,
            jobTypeId: null,
            regionId: null,
            skillIds: [],
        }
    );

    useEffect(() => {
        if (!jobPost) return;
        setFormJobPost({
            title: jobPost.title ?? "",
            description: jobPost.description ?? "",
            quantity: jobPost.quantity ?? 0,
            salary: jobPost.salary ?? 0,
            salaryType: jobPost.salaryType ?? null,
            status: jobPost.status ?? null,
            endedTime: jobPost.endedTime ?? "",
            industryId: jobPost.industry?.id ?? null,
            levelId: jobPost.level?.id ?? null,
            jobTypeId: jobPost.jobType?.id ?? null,
            regionId: jobPost.region?.id ?? null,
            skillIds: jobPost.skills?.map(s => s.id) ?? [],
        })
    }, [jobPost]);

    const [errors, setErrors] = useState({});
    const regions = useList(getRegionsNotPagination);
    const jobTypes = useList(getJobTypesNotPagination);
    const industries = useList(getIndustriesNotPagination);
    const levels = useList(getLevelsNotPagination);
    const skills = useList(getSkillsNotPagination);

    const handleChange = (e) => {
        const {name, value} = e.target;
        setFormJobPost((prev) => ({...prev, [name]: value}));
    };

    const validate = () => {
        const newErrors = {};

        // 1. Các field không được rỗng
        if (!formJobPost.title.trim())
            newErrors.title = "Tiêu đề không được để trống";
        if (!formJobPost.description.trim())
            newErrors.description = "Mô tả không được để trống";
        if (!formJobPost.industryId)
            newErrors.industryId = "Vui lòng chọn ngành nghề";
        if (!formJobPost.levelId) newErrors.levelId = "Vui lòng chọn cấp bậc";
        if (!formJobPost.jobTypeId) newErrors.jobTypeId = "Vui lòng chọn loại nghề";
        if (!formJobPost.regionId) newErrors.regionId = "Vui lòng chọn khu vực";
        if (!formJobPost.salaryType)
            newErrors.salaryType = "Vui lòng chọn loại lương";

        // 2. Số lượng >= 1
        if (formJobPost.quantity < 1)
            newErrors.quantity = "Số lượng phải lớn hơn hoặc bằng 1";

        // 3. Mức lương > 0 và salaryMin < salaryMax
        if (formJobPost.salary <= 0)
            newErrors.salaryMin = "Mức lương từ phải lớn hơn 0";

        // 4. Hạn nộp hồ sơ (datetime) phải > hiện tại
        if (!formJobPost.endedTime) {
            newErrors.endedTime = "Vui lòng chọn hạn nộp hồ sơ";
        } else {
            const endedTimeDate = new Date(formJobPost.endedTime);
            const now = new Date();
            if (endedTimeDate <= now) {
                newErrors.endedTime = "Hạn nộp hồ sơ phải lớn hơn thời gian hiện tại";
            }
        }

        setErrors(newErrors);

        // Nếu không có lỗi → trả về true
        return Object.keys(newErrors).length === 0;
    };


    const handleSkillsChange = (e) => {
        const skillId = Number(e?.target?.value ?? e);
        if (!skillId) return;

        setFormJobPost(prev => {
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
        setFormJobPost(prev => ({
            ...prev,
            skillIds: prev.skillIds.filter(id => id !== skillId)
        }));
    };


    const handleSubmit = async () => {
        if (!validate()) {
            console.log("Form có lỗi:", errors);
            return;
        }
        try {
            const res = await updateJobPost(jobPost.id, formJobPost);

            if (res.code && res.code !== 1000) {
                toast.error(res.message || "Cập nhật thất bại!");
                return;
            }

            console.log("Cập nhật job post thành công:", res);
            toast.success("Cập nhật bài đăng thành công!");

            if (onUpdate) {
                onUpdate(res.result);
            }
            onClose();
        } catch (error) {
            console.error("Update failed:", error);
            toast.error(error.response?.data?.message || "Cập nhật thất bại!");
        }
    };

    return (
        <Dialog open={open} onClose={onClose} className="relative z-50">
            <div className="fixed inset-0 bg-black/30" aria-hidden="true"/>
            <div className="fixed inset-0 flex items-center justify-center p-4">
                <DialogPanel className="w-full max-w-5xl bg-gray-100 rounded-2xl p-6 max-h-[90vh] overflow-y-auto">
                    <DialogTitle className="text-lg font-bold mb-4">
                        <PrimaryTitle>Cập nhật tin tuyển dụng</PrimaryTitle>
                    </DialogTitle>

                    <div className="flex gap-3">
                        <div className="flex-1 flex flex-col gap-2 bg-white p-3 rounded-2xl">
                            <SecondTitle>Thông tin bài đăng</SecondTitle>
                            <FormInput
                                label="Tiêu đề"
                                name="title"
                                value={formJobPost.title}
                                error={errors.title}
                                onChange={handleChange}
                            />
                            <RichTextEditor
                                label={"Mô tả công việc"}
                                value={formJobPost.description}
                                error={errors.description}
                                onChange={(value) =>
                                    setFormJobPost((prev) => ({...prev, description: value}))
                                }
                                placeholder={"Mô tả công việc"}
                            />
                            <FormInput
                                label="Số lượng"
                                name="quantity"
                                type="number"
                                value={formJobPost.quantity}
                                onChange={handleChange}
                                error={errors.quantity}
                            />
                            <FormInput
                                label="Mức lương"
                                name="salary"
                                type="number"
                                value={formJobPost.salary}
                                onChange={handleChange}
                                error={errors.salary}
                            />
                            <FormSelect
                                label="Loại lương"
                                name="salaryType"
                                selected={SALARY_TYPE.find(
                                    (j) => j.code === formJobPost.salaryType
                                )}
                                onChange={handleChange}
                                options={SALARY_TYPE}
                                placeholder="Chọn loại lương"
                                error={errors.salaryType}
                            />
                            <FormInput
                                label="Hạn nộp hồ sơ"
                                name="endedTime"
                                type="datetime-local"
                                value={formJobPost.endedTime}
                                onChange={handleChange}
                                error={errors.endedTime}
                            />
                            {/* Trạng thái bài đăng */}
                            <FormSelect
                                label="Trạng thái bài đăng"
                                name="status"
                                selected={STATUS_JOB_POST.find(
                                    (j) => j.code === formJobPost.status
                                )}
                                onChange={handleChange}
                                options={STATUS_JOB_POST}
                                placeholder="Chọn trạng thái"
                                error={errors.status}
                            />
                        </div>
                        <div className="flex-1 flex flex-col gap-2 bg-white p-3 rounded-2xl ">
                            <SecondTitle>Danh mục</SecondTitle>

                            <FormSelect
                                label="Ngành nghề"
                                name="industryId"
                                selected={industries.data.find(
                                    (j) => j.id === formJobPost.industryId
                                )}
                                onChange={handleChange}
                                options={industries.data}
                                placeholder="Chọn ngành nghề"
                                error={errors.industryId}
                            />

                            <FormSelect
                                label="Cấp bậc"
                                name="levelId"
                                selected={levels.data.find((j) => j.id === formJobPost.levelId)}
                                onChange={handleChange}
                                options={levels.data}
                                placeholder="Chọn cấp bậc"
                                error={errors.levelId}
                            />
                            <FormSelect
                                label="Loại nghề"
                                name="jobTypeId"
                                selected={jobTypes.data.find((j) => j.id === formJobPost.jobTypeId)}
                                onChange={handleChange}
                                options={jobTypes.data}
                                placeholder="Chọn loại nghề"
                                error={errors.jobTypeId}
                            />
                            <FormSelect
                                label="Khu vực"
                                name="regionId"
                                selected={regions.data.find((r) => r.id === formJobPost.regionId)}
                                onChange={handleChange}
                                options={regions.data}
                                placeholder="Chọn khu vực"
                                error={errors.regionId}
                            />

                            <div className="mt-3">
                                <SecondTitle></SecondTitle>
                                <span>Kỹ năng</span>
                                {formJobPost.skillIds.length > 0 && (
                                    <SkillList
                                        skillIds={formJobPost.skillIds}
                                        allSkills={skills.data}
                                        onRemove={handleRemoveSkill}
                                    />
                                )}
                                <div className="mt-3">
                                    <FormSelect
                                        label="Thêm kỹ năng"
                                        selected={null}
                                        options={skills.data?.filter(
                                            skill => !formJobPost.skillIds.includes(skill.id)
                                        )}
                                        placeholder="Chọn kỹ năng để thêm"
                                        onChange={handleSkillsChange}
                                    />
                                </div>
                            </div>

                            <div className="flex justify-end gap-3 mt-5">
                                <PrimaryButton variant="outline" onClick={onClose}>
                                    Hủy
                                </PrimaryButton>
                                <PrimaryButton onClick={handleSubmit}>
                                    Cập nhật
                                </PrimaryButton>
                            </div>
                        </div>
                    </div>
                </DialogPanel>
            </div>
        </Dialog>
    );
};
export default UpdateJobPostDialog;
