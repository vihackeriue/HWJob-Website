import { tGlobal } from "../../../utils/translator";

const IntroduceFeatureCard = ({ img, content }) => {
  return (
    <div className="flex flex-col md:flex-row gap-3 items-center p-4 bg-stoneBrown-900/50 rounded-2xl">
      <img src={img} alt="feature" className="w-24" />
      <div>
        <h1 className="text-2xl md:text-3xl text-teal-900 dark:text-teal-100 font-semibold  text-center md:text-left">
          {tGlobal(`${content}.title`)}
        </h1>

        <p className="hidden md:block">{tGlobal(`${content}.desc`)}</p>
        <div className="flex justify-center md:justify-end mt-3 md:mt-0">
          <a href="#" className="text-md font-semibold hover-underline">
            {tGlobal("common.more")}
          </a>
        </div>
      </div>
    </div>
  );
};

export default IntroduceFeatureCard;
