import { Episode } from "./episode";

export interface Season {
    Title?: string;
    Season?: number;
    totalSeasons?: number;
    Episodes?: Episode[];
}
