import { Response } from 'got';

export default class ApiVideoError extends Error {
    private problemDetails: string | null;
    private code: number | null;

    constructor(response?: Response) {
        const { statusCode, body: problemDetails } = response;

        super(problemDetails.title);

        this.problemDetails = problemDetails;
        this.code = statusCode;
        this.stack = new Error().stack;
    }
}


